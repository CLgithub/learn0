package com.cl.learn.factorypattern.abstractfactory2.product;

/**
 * @Author l
 * @Date 2022/3/19 21:33
 */
public class CarBMW implements Car {

    @Override
    public void run() {
        System.out.println("宝马 汽车");
    }
}
