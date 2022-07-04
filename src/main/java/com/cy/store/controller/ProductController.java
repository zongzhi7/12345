package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.entity.Product;
import com.cy.store.service.IProductService;
import com.cy.store.service.impl.AddressServiceImpl;
import com.cy.store.service.impl.ProductServiceImpl;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("products")
public class ProductController extends BaseController {
    @Autowired
    private ProductServiceImpl productService;
    @RequestMapping("hotList")
    public JsonResult<List<Product>> getHotList(){
        List<Product> data=productService.findHotList();
        return  new JsonResult<List<Product>>(OK,data);
    }
    @RequestMapping("newList")
    public JsonResult<List<Product>> getNewList(){
        List<Product> data=productService.findNewList();
        return  new JsonResult<List<Product>>(OK,data);
    }
    @GetMapping("{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id){
        Product data = productService.findById(id);
        return new JsonResult<Product>(OK,data);
    }

}