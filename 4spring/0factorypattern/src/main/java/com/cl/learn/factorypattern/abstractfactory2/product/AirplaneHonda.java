package com.cl.learn.factorypattern.abstractfactory2.product;

/**
 * @Author l
 * @Date 2022/3/19 21:34
 */
public class AirplaneHonda implements Airplane{
    @Override
    public void fly() {
        System.out.println("本田 飞机");
    }
}
