package com.cl.learn.factorypattern.abstractfactory2.factory;

import com.cl.learn.factorypattern.abstractfactory2.product.Airplane;
import com.cl.learn.factorypattern.abstractfactory2.product.Car;

/**
 * @Author l
 * @Date 2022/3/19 21:27
 */
public interface AbstractFactory {
    Car createCar();
    Airplane createAirplane();
}
