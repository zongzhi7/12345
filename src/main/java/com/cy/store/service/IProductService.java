package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findHotList();
    List<Product> findNewList();
    Product findById(Integer id);
}
