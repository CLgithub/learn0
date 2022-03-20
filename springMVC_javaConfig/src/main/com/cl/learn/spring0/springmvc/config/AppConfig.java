package com.cl.learn.spring0.springmvc.config;

import com.cl.learn.spring0.servlet.Servlet1;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author l
 * @Date 2022/3/13 15:30
 */
@Configurable
@ComponentScan("com")
public class AppConfig extends WebMvcConfigurationSupport {

    @Bean(name = "servlet1")
    public Servlet1 servlet1(){
        return new Servlet1();
    }
}
