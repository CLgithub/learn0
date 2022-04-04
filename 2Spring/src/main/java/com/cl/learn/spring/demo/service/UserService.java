package com.cl.learn.spring.demo.service;

import com.cl.learn.spring.demo.entity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author l
 * @Date 2022/4/1 10:34
 */
@Component
public class UserService implements InitializingBean {

    @Autowired //可以在构造方法中去获取
    private OrderService orderService1;

    public UserService(){
        System.out.println(0);
    }

//    @Autowired
    public UserService(OrderService orderService12) {
        System.out.println(1);
        this.orderService1 = orderService12;
    }

    public UserService(OrderService orderService, OrderService orderService2) {
        System.out.println(2);
        this.orderService1 = orderService;
    }

    @Autowired
    private User admin;

    @PostConstruct // 标注后，在bean 初始化前 就会执行该方法
    public void a(){

    }

    // 初始化
    @Override
    public void afterPropertiesSet() throws Exception {
        // mysql-->管理员信息--->User对象--->admin
    }

    public void test(){
        System.out.println(orderService1);
    }


}
