package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        // 根据参数user对象获取注册的用户名
        String username = user.getUsername();
        // 调用持久层的User findByUsername(String username)方法，根据用户名查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否不为null
        if (result != null) {
            // 是：表示用户名已被占用，则抛出UsernameDuplicateException异常
            throw new UsernameDuplicationException("尝试注册的用户名[" + username + "]已经被占用");
        }
        String oldPassword = user.getPassword();
        //随机生成一个盐值
        String salt=UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        //密码加盐值
        String MD5Password=getMD5Password(oldPassword,salt);
        user.setPassword(MD5Password);
        // 创建当前时间对象
        Date now = new Date();

        // 补全数据：isDelete(0)
        user.setIsDelete(0);
        // 补全数据：4项日志属性
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);

        // 表示用户名没有被占用，则允许注册
        // 调用持久层Integer insert(User user)方法，执行注册并获取返回值(受影响的行数)
        Integer rows = userMapper.insert(user);
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 是：插入数据时出现某种错误，则抛出InsertException异常
            throw new InsertException("添加用户数据出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public User login(String username, String password) {
        //先根据用户名称来查询用户的数据是否存在，如果不在则抛异常
        User result=userMapper.findByUsername(username);
        if (result==null){
            throw new UserNotFoundException("用户不存在");
        }
        //检测用户的密码是否匹配
        //1.先获取数据库中加密的密码
        String oldPassword= result.getPassword();
        //2.和用户传递过来的密码进行比较
        //2.1先获取该用户密码唯一的盐值（）
        String salt = result.getSalt();
        //2.2将用户的密码用MD5算法进行加密
        String newMD5Password =getMD5Password(password,salt);
        //将密码进行比较
        if(!newMD5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误，登录失败");

        }
        //判断用户的isDelete的值是否为1
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("该用户不存在");

        }
        //开始查询,调用mapper层的方法
        User user=new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        //找到后，将用户数据返回
        return user;
    }

    @Override
    public void changePassword(Integer uid,
                               String username,
                               String newPassword,
                               String oldPassword) {
        User result=userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户找不到啦");
        }
        //原始密码跟数据库密码进行比对
        String oldMD5Password = getMD5Password(oldPassword,result.getSalt());
        if(!result.getPassword().equals(oldMD5Password)){
            throw new PasswordNotMatchException("密码找不到啦");
        }
        // 将新的的密码设置进数据库，再进行加密跟新
        String newMD5Password = getMD5Password(newPassword,result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid,newMD5Password,result.getModifiedUser(),new Date());
        if(rows!=1){
            throw new UpdateException("跟新数据时产生未知的异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result=userMapper.findByUid(uid);
        if (result==null){
            throw new UserNotFoundException("用户信息未找到");
        }
        return result;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result=userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户信息未找到");
        }
        user.setUid(uid);
        user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows=userMapper.updateInfoByUid(user);
        if(rows!=1){
            throw  new UpdateException("更新数据时产生未知异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result=userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows=userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if(rows!=1){
            throw new UpdateException("更新数据时产生未知异常");
        }
    }


    private String getMD5Password(String password, String salt){
        for (int i=0;i<3;i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }



}
