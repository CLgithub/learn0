package com.cl.learn.springboot.springbootdemo4.config;

import com.cl.learn.springboot.springbootdemo4.entity.User;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author l
 * @Date 2022/5/14 23:09
 */
@SpringBootConfiguration
@EnableConfigurationProperties(User.class)
public class Myconfig {

}
