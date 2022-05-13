package com.cl.learn.springboot.springbootdemo3mystartertest;

import com.cl.learn.springboot.springbootdemo3mystarter.server.Service1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author l
 * @Date 2022/5/13 14:24
 */
@SpringBootApplication
public class MyStarterTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MyStarterTestApplication.class, args);
        Service1 bean = applicationContext.getBean(Service1.class);
        String s = bean.test1("abc");
        System.out.println(s);
    }
}
