package com.cl.learn.springboot.springbootdemo1.entity;

/**
 * @Author l
 * @Date 2022/5/12 10:49
 */
public class Order {
    private int id;
    private String str1;

    public Order(){}

    public Order(int id, String str1) {
        this.id = id;
        this.str1 = str1;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }
}
