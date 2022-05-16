package com.cl.learn.springboot.springbootdemo5;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author l
 * @Date 2022/5/16 10:33
 */
@SpringBootApplication
public class ProcedureApplication implements CommandLineRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ProcedureApplication.class, args);
        System.out.println("返回Context："+run);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("callRunners");
    }
}
