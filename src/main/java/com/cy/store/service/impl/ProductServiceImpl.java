package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.Product;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IProductService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        List<Product> list =productMapper.findHotList();
        for (Product product:list){
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public List<Product> findNewList() {
        List<Product> list =productMapper.findNewList();
        for (Product product:list){
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setModifiedUser(null);
        }

        return list;
    }

    @Override
    public Product findById(Integer id) {
        Product product=productMapper.findById(id);
        if(product==null) {
            throw new ProductNotFoundException("商品信息不存在异常");
        }
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);

        return product;
    }
}
