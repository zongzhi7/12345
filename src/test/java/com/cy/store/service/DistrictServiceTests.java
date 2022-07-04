package com.cy.store.service;


import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.impl.AddressServiceImpl;
import com.cy.store.service.impl.DistrictServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest

@RunWith(SpringRunner.class)

public class DistrictServiceTests {
    @Autowired

    private DistrictServiceImpl districtService;

    @Test
    public void getByParent() {
        try {
            String parent = "86";
            List<District> list = districtService.getByParent(parent);
            System.out.println("count=" + list.size());
            for (District item : list) {
                System.out.println(item);
            }
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void getNameByCode(){
        System.out.println(districtService.getNameByCode("120102"));
    }
}
