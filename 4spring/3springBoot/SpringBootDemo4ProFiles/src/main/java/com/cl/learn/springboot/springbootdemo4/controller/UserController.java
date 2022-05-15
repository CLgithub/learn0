package com.cl.learn.springboot.springbootdemo4.controller;

import com.cl.learn.springboot.springbootdemo4.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @Author l
 * @Date 2022/5/14 23:14
 */
@RestController
@RequestMapping
public class UserController  {

    @Autowired
    private UserService userService;

    @RequestMapping("/getName")
    public String getName(){
        return userService.getName();
    }


}
