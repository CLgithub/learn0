package com.cl.learn.spring0.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationAttributes;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * springBoot 相对于spring
 *  全注解
 *  利用javaConfig，将之前的xml配置全部改为javaConfig配置
 *  自动配置
 *
 *
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
