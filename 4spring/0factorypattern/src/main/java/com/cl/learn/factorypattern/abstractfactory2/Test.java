package com.cl.learn.factorypattern.abstractfactory2;

import com.cl.learn.factorypattern.abstractfactory2.factory.AbstractFactory;
import com.cl.learn.factorypattern.abstractfactory2.factory.FactoryBMW;
import com.cl.learn.factorypattern.abstractfactory2.factory.FactoryHonda;
import com.cl.learn.factorypattern.abstractfactory2.product.Airplane;
import com.cl.learn.factorypattern.abstractfactory2.product.Car;

/**
 * 自己实现一遍
 * 原文地址：https://github.com/Jstarfish/JavaKeeper/blob/master/docs/design-pattern/Factory-Pattern.md
 * @Author l
 * @Date 2022/3/19 21:38
 */
public class Test {
    public static void main(String[] args) {
        AbstractFactory factory=new FactoryHonda();
        Airplane airplane = factory.createAirplane();
        airplane.fly();

        AbstractFactory factory1=new FactoryBMW();
        Car car = factory1.createCar();
        car.run();

    }
}
