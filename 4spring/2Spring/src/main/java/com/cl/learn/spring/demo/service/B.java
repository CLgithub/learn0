package com.cl.learn.spring.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @Author l
 * @Date 2022/4/16 14:30
 */
@Component
public class B {

    @Autowired
    private A a;


}
