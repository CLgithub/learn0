package com.cl.learn.factorypattern.abstractfactory2.product;

/**
 * @Author l
 * @Date 2022/3/19 21:32
 */
public class CarHonda implements Car{
    @Override
    public void run() {
        System.out.println("本田 汽车");
    }
}
