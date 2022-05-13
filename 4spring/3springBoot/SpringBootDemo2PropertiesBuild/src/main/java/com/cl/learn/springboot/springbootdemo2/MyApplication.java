package com.cl.learn.springboot.springbootdemo2;

import com.cl.learn.springboot.springbootdemo2.properties.P1;
import com.cl.learn.springboot.springbootdemo2.properties.P2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author l
 * @Date 2022/5/13 13:45
 */
@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MyApplication.class, args);
        P1 p1 = applicationContext.getBean(P1.class);
        System.out.println(p1.getK1());
        System.out.println(p1.getK2());
        P2 p2 = applicationContext.getBean(P2.class);
        System.out.println(p2.getK1());
        System.out.println(p2.getK2());
    }
}
