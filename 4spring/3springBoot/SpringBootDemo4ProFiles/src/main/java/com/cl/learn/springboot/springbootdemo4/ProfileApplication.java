package com.cl.learn.springboot.springbootdemo4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

/**
 * @Author l
 * @Date 2022/5/14 23:08
 */
@SpringBootApplication
public class ProfileApplication implements CommandLineRunner {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        SpringApplication.run(ProfileApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Arrays.toString(args));
    }
}
