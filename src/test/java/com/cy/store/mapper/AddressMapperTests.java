package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;
    @Test
    public void insert(){
        Address address =new Address();
        address.setUid(18);
        address.setPhone("333");
        address.setName("女朋友");
        addressMapper.insert(address);
    }
    @Test
    public void countByUid(){
        Integer count=addressMapper.countByUid(18);
        System.out.println(count);
    }
    @Test
    public void findByUid(){
        List<Address> list=addressMapper.findByUid(18);
        System.out.println(list);
    }
}
