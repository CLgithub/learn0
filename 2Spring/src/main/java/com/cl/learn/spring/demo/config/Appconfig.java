package com.cl.learn.spring.demo.config;

import com.cl.learn.spring.demo.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author l
 * @Date 2022/4/1 10:51
 */
@ComponentScan("com.cl.learn.spring.demo")
@EnableAspectJAutoProxy
public class Appconfig {

    @Bean
    public OrderService orderService1(){
        return new OrderService();
    }

    @Bean
    public OrderService orderService2(){
        return new OrderService();
    }
}
