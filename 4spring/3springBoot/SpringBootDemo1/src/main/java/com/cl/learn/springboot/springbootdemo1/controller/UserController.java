package com.cl.learn.springboot.springbootdemo1.controller;

import com.cl.learn.springboot.springbootdemo1.entity.User;
import com.cl.learn.springboot.springbootdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author l
 * @Date 2022/5/9 23:05
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public User test(){
        return userService.test();
    }
}
