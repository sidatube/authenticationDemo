package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/admin")
public class AdminController {
    @Autowired
    private AccountService accountService;
    private static final List<Product> products;
    static {
        products  = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            products.add(Product.builder()
                    .id(i + 1).name("Product" + i + 1).price(i * 10000)
                    .build());
        }
    }
    @GetMapping
    public String hell() {
        return "welcome to home ";
    }
    @GetMapping(path = "all")
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(accountService.findAll());
    }
    @GetMapping(path = "/product")
//    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Object> getProduct() {
        return ResponseEntity.ok(products);
    }

}

