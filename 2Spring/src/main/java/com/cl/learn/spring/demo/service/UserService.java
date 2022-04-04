package com.cl.learn.spring.demo.service;

import com.cl.learn.spring.demo.entity.User;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.AopProxy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @Author l
 * @Date 2022/4/1 10:34
 */
@Component
public class UserService implements InitializingBean {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

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

    @Transactional
    public void test2(){
        jdbcTemplate.execute("INSERT into t1 values(1,1,1,1,'1')");
//        throw new NullPointerException();
        userService.b();
    }

    // 按理说NEVER 执行b方法会抛异常，但却没有，这是因为执行b方法是被target对象调用的，所以事务失效，而执行test2()时，是代理对象调用
    // 想要生效，可以自己注入自己 就可以使用代理对象来调用b()方法，还可以获取自己的代理对象 AopContext.currentProxy();
    @Transactional(propagation = Propagation.NEVER) // NEVER 当有事务时，就抛异常
    public void b(){
        jdbcTemplate.execute("INSERT into t1 values(2,1,1,1,'1')");
    }


}
