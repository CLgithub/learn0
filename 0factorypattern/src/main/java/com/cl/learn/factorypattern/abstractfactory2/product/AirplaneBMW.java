package com.cl.learn.factorypattern.abstractfactory2.product;

/**
 * @Author l
 * @Date 2022/3/19 21:35
 */
public class AirplaneBMW implements Airplane{
    @Override
    public void fly() {
        System.out.println("宝马 飞机");
    }
}
