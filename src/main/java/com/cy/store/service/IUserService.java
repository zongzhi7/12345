package com.cy.store.service;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/* 用户模块业务层接口*/
public interface IUserService {
    /**
     * 用户注册
     * @param user 用户数据
     */
    void reg(User user);

    /**
     * 用户登录功能
     * @param username 用户名
     * @param password  用户密码
     * @return 当前匹配的用户数据，如果没有则返回null
     */
    User login(String username,String password);

    void changePassword(Integer uid,
                        String username,
                        String newPassword,
                        String oldPassword);

    /**
     * 根据用户的id查询数据
     * @param uid 用户id
     * @return user
     */
    User getByUid(Integer uid);

    /**
     * 更新用户数据
     * @param uid 用户id
     * @param username 用户名字
     * @param user 用户对象数据
     */
    void changeInfo(Integer uid,String username,User user);

    /**
     * 修改用户的头像
     * @param uid 用户id
     * @param avatar 用户头像路径
     * @param username
     */
    void changeAvatar(Integer uid,String avatar, String username);
}
