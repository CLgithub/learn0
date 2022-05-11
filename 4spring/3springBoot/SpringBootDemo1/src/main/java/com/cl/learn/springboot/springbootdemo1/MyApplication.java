package com.cl.learn.springboot.springbootdemo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author l
 * @Date 2022/5/9 18:05
 */
@SpringBootApplication
@MapperScan("com.cl.learn.springboot.springbootdemo1.mapper")
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

}