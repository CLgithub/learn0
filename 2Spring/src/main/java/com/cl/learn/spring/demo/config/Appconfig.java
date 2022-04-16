package com.cl.learn.spring.demo.config;

import com.cl.learn.spring.demo.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @Author l
 * @Date 2022/4/1 10:51
 */
@ComponentScan("com.cl.learn.spring.demo")
@EnableAspectJAutoProxy // 开启切面
@EnableTransactionManagement    // 开启事务管理
@Configuration
@EnableAsync
public class Appconfig {

//    @Bean
//    public OrderService orderService1(){
//        return new OrderService();
//    }

//    @Bean
//    public OrderService orderService2(){
//        return new OrderService();
//    }

    // 为数据源提供jdbctemplate
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    // 为数据源添加事务管理器
    @Bean
    public PlatformTransactionManager transactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    // 数据源
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.3.215:3306/spring0?characterEncoding=UTF-8");
//        dataSource.setUrl("jdbc:mysql://homeUbuntu:3306/spring0");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }
}
