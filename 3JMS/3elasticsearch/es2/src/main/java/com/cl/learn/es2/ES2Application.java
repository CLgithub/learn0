package com.cl.learn.es2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.cl.learn.es2.repo")
public class ES2Application {
    public static void main(String[] args) {
        SpringApplication.run(ES2Application.class, args);
    }
}
