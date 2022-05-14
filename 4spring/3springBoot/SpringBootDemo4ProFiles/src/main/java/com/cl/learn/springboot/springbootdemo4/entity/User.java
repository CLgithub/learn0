package com.cl.learn.springboot.springbootdemo4.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author l
 * @Date 2022/5/14 23:11
 */
@ConfigurationProperties(prefix = "my")
public class User {

    private String name;

    public User(){}

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
