package com.cy.store.service;


import com.cy.store.entity.Address;
import com.cy.store.service.impl.AddressServiceImpl;
import com.cy.store.service.impl.CartServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest

@RunWith(SpringRunner.class)

public class CartServiceTests {
    @Autowired

    private CartServiceImpl cartService;

    @Test
    public void addToCart(){
        cartService.addToCart(22,10000001,5,"朴彩英");
    }

}
