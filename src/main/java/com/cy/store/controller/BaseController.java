package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    public static final  int OK = 200;
    @ExceptionHandler({ServiceException.class,FileUploadException.class})//统一处理异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>();
        if (e instanceof UsernameDuplicationException){
            result.setState(4000);
            result.setMessage("用户名被占用");
        }else if (e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMessage("用户不存在");
        }else if (e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("用户名的密码错误异常");
        }else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("用户收货地址超出上限的异常");
        }else if (e instanceof ProductNotFoundException) {
            result.setState(4004);
            result.setMessage("商品数据不存在的异常");
        }else if (e instanceof UpdateException){
            result.setState(5001);
            result.setMessage("更新数据时产生未知异常");
        } else if (e instanceof InsertException ){
            result.setState(5000);
            result.setMessage("产生未知异常");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage("文件类型不支持");
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
            return result;
    }
    protected final Integer getuidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getusernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
