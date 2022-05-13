package com.cl.learn.springboot.springbootdemo2.config;

import com.cl.learn.springboot.springbootdemo2.properties.P1;
import com.cl.learn.springboot.springbootdemo2.properties.P2;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author l
 * @Date 2022/5/13 13:47
 */
@SpringBootConfiguration
@EnableConfigurationProperties({P1.class,P2.class})
//@ConfigurationPropertiesScan("com.cl.learn.springboot.springbootdemo2.properties")
public class MyConfig {
}
