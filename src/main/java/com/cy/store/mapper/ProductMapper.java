package com.cy.store.mapper;

import com.cy.store.entity.Product;

import java.util.List;

public interface ProductMapper {
    List<Product> findHotList();
    List<Product> findNewList();
    Product findById(Integer id);
}
