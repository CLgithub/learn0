package com.cl.learn.springboot.springbootdemo3mystarter.server;

import com.cl.learn.springboot.springbootdemo3mystarter.properties.P1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author l
 * @Date 2022/5/13 14:10
 */
public class Service1 {

    @Autowired
    private P1 p1;

    public String test1(String str){
        return p1.getStr1()+str+p1.getStr2();
    }
}
