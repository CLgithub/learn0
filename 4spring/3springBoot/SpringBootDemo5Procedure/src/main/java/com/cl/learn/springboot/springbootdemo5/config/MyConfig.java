package com.cl.learn.springboot.springbootdemo5.config;

import com.cl.learn.springboot.springbootdemo5.entity.TestBean2;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @Author l
 * @Date 2022/5/16 10:34
 */
@SpringBootConfiguration
public class MyConfig {

    public MyConfig() {
        System.out.println("解析配置类");
    }

    @Bean
    public TestBean2 testBean2(){
        System.out.println("解析@bean");
        return new TestBean2();
    }


}
