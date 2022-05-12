package com.cl.learn.springboot.springbootdemo1;

import com.cl.learn.springboot.springbootdemo1.entity.Order;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author l
 * @Date 2022/5/9 18:05
 */
@SpringBootApplication
@MapperScan("com.cl.learn.springboot.springbootdemo1.mapper")
public class MyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MyApplication.class, args);
        Order bean = applicationContext.getBean(Order.class);
        System.out.println(bean);
        Order bean2 = applicationContext.getBean(Order.class);
        System.out.println(bean2);
    }

}