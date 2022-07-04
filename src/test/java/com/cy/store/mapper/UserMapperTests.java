package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void insert(){
        User user=new User();
        user.setUsername("zongzhi");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }
    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("zongzhi");
        System.out.println(user);
    }
    @Test
    public void updatePasswordUserByUid(){
         userMapper.updatePasswordByUid(14,"321","系统管理员",new Date());
    }
    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(14));
    }
    @Test
    public void updateInfoByUid(){
        User user=new User();
        user.setUid(17);
//        user.setUsername("ceshi8");
        user.setPhone("18858867888");
        user.setEmail("884884@111.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }
    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(15,"/112","哥哥",new Date());


    }
}

