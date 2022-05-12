package com.cl.learn.springboot.other;

/**
 * @Author l
 * @Date 2022/5/12 10:14
 */

import com.cl.learn.springboot.springbootdemo1.entity.Order;
import com.cl.learn.springboot.springbootdemo1.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;



//@SpringBootConfiguration(proxyBeanMethods = false)
//@ImportResource("spring.xml")   // 引入资源
//@Import(Order.class)    // 导入一个bean
public class MyConfig {

//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        return driverManagerDataSource;
//    }

//    @Bean
//    public User user1(){
//        System.out.println(order());
//        return new User();
//    }
//
//    @Bean
//    public User user2(){
//        System.out.println(order());
//        return new User();
//    }
//
    @Bean
    public Order order(){
        return new Order();
    }

}
