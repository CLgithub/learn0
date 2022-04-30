package com.cl.learn.spring.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author l
 * @Date 2022/4/16 14:29
 */
@Component
public class A {

    @Autowired
    private B b;



//    @Async // 会去创建另一个代理，和解决循环依赖的不是一套，所以报错， 解决办法 @Lazy
    public void test(){
        System.out.println(b);
    }
}
