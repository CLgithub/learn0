package com.cl.learn.factorypattern.abstractfactory2.factory;

import com.cl.learn.factorypattern.abstractfactory2.product.Airplane;
import com.cl.learn.factorypattern.abstractfactory2.product.AirplaneBMW;
import com.cl.learn.factorypattern.abstractfactory2.product.Car;
import com.cl.learn.factorypattern.abstractfactory2.product.CarBMW;

/**
 * @Author l
 * @Date 2022/3/19 21:37
 */
public class FactoryBMW implements AbstractFactory{
    @Override
    public Car createCar() {
        return new CarBMW();
    }

    @Override
    public Airplane createAirplane() {
        return new AirplaneBMW();
    }
}
