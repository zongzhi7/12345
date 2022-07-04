package com.cy.store.service;


import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest

@RunWith(SpringRunner.class)

public class UserServiceTests {
    @Autowired

    private UserServiceImpl userService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("gouba");
            user.setPassword("123");
            user.setGender(0);
            user.setPhone("17858802974");
            user.setEmail("lower@cy.cn");
            user.setAvatar("avatar.png");
            userService.reg(user);
            System.out.println("注册成功！");
        } catch (ServiceException e) {
            System.out.println("注册失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void login(){
        User user=userService.login("choupi","123");
        System.out.println(user);
    }
    @Test
    public void changePassword(){
         userService.changePassword(16,"gouba","321","123");
    }
    @Test
    public void getByUid(){
        System.out.println(userService.getByUid(17));
    }
    @Test
    public void changeInfo(){
        User user=new User();
//        user.setUsername("ceshi888");
        user.setPhone("8888000888");
        user.setEmail("7489415@456.com");
        user.setGender(0);
        userService.changeInfo(17,"ceshi2",user);
    }
    @Test
    public void changeAvatar(){
        userService.changeAvatar(15,"/1557/da.png","小虎");
    }
}
