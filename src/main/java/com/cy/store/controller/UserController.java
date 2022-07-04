package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicationException;
import com.cy.store.service.impl.UserServiceImpl;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private UserServiceImpl userService;
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
    }
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password ,HttpSession session){
        User data=userService.login(username,password);

        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
//        System.out.println(getuidFromSession(session));
//        System.out.println(getusernameFromSession(session));
        return new JsonResult<User>(OK,data);
    }
    @RequestMapping("changePassword")
    public JsonResult<Void> changePassword(String newPassword,
                                           String oldPassword,
                                           HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getusernameFromSession(session);
        userService.changePassword(uid,username,newPassword,oldPassword);
        return new JsonResult<Void>(OK);
    }
    @RequestMapping("getByUid")
    public JsonResult<User> getByUid(HttpSession session){
        Integer uid =getuidFromSession(session);
        User data=userService.getByUid(uid);
        return new JsonResult<User>(OK,data);
    }
    @RequestMapping("changeInfo")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user里面有username,phone,email,gender
        Integer uid =getuidFromSession(session);
        String username=getusernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<Void>(OK);
    }
    //最大值
    public  static  final  int AVATAR_MAX_SIZE = 10*1024*1024;
    //类型
    public static final List<String> AVATAR_TYPE =
            new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/bmp");
    }
    @RequestMapping("changeAvatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file){
        //判断文件是否为空
        if(file.isEmpty()){
            throw new FileEmptyException("空");
        }
        if(file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("文件不符合");
        }
        String contentType=file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        //上传文件
        String parent=
                session.getServletContext().getRealPath("upload");
        File dir =new File(parent);//File对象指向这个路径
        if (!dir.exists()){//检测目录是否存在
            dir.mkdir();//创建目录
        }
        //获取文件名称，UUID工具生成一个新的字符串作为文件名
        String originalFilename=file.getOriginalFilename();
        System.out.println(originalFilename);
        String suffix=originalFilename.split("\\.")[1];
        //例：AUEHC-NAKKFHA-YQUIRH-AKNCHKA.png
        String filename=UUID.randomUUID().toString().toUpperCase()+suffix;
        File dest= new File(dir ,filename);
        try {
            file.transferTo(dest);//将file文件中的数据写入到dest文件
//        }catch (FileStateException e){
//            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        Integer uid =getuidFromSession(session);
        String username=getusernameFromSession(session);
        String avatar="/upload/"+filename;
        userService.changeAvatar(uid,avatar,username);
        System.out.println(parent);
        //返回用户的头像给前端
        return new JsonResult<String>(OK,avatar);
    }
}
