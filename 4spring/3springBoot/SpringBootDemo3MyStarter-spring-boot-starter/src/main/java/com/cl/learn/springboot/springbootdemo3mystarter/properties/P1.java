package com.cl.learn.springboot.springbootdemo3mystarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author l
 * @Date 2022/5/13 14:44
 */
@ConfigurationProperties(prefix = "mystarter1")
public class P1 {
    private String str1="str1-";
    private String str2;

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }
}
