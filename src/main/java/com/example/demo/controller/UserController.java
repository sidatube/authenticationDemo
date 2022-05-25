package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.Article;
import com.example.demo.entity.Product;
import com.example.demo.service.AccountService;
import com.example.demo.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/user")
public class UserController {
    private static final List<Article> articles;
    static {
        articles  = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            articles.add(Article.builder()
                    .id(i + 1).title("Article" + i + 1).description("ASDASDASD")
                    .build());
        }
    }
    @Autowired
    private AccountService accountService;
    @GetMapping
    public String hell(){
        return "welcome to home ";
    }
    @GetMapping(path = "/articles")
    public ResponseEntity<Object> getArticle() {
        return ResponseEntity.ok(articles);
    }
    @PostMapping
    public ResponseEntity<Account> register(@RequestBody Account account){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.save(account));
    }
    @GetMapping(path = "/info")
    public ResponseEntity<Object> getInformation(){
         Account account = accountService.getInfo();
        return ResponseEntity.ok(account);
    }
}

