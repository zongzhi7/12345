package com.cy.store.service;


import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.impl.AddressServiceImpl;
import com.cy.store.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest

@RunWith(SpringRunner.class)

public class AddressServiceTests {
    @Autowired

    private AddressServiceImpl addressService;

    @Test
    public void addNewAddress(){
        Address address =new Address();
        address.setPhone("8844787");
        address.setName("朴彩英的家");
        address.setAreaName("道滘");
        address.setCityName("东莞");
        address.setProvinceName("广东");
        addressService.addNewAddress(18,"权志龙",address);
    }

}
