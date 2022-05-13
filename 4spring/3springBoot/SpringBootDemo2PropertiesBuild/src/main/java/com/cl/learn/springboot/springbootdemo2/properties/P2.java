package com.cl.learn.springboot.springbootdemo2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author l
 * @Date 2022/5/13 13:49
 */
@ConfigurationProperties(prefix = "p2")
public class P2 {

    private int k1;
    private String k2;


    public int getK1() {
        return k1;
    }

    public void setK1(int k1) {
        this.k1 = k1;
    }

    public String getK2() {
        return k2;
    }

    public void setK2(String k2) {
        this.k2 = k2;
    }
}
