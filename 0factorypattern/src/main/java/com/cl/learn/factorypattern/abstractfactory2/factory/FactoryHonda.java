package com.cl.learn.factorypattern.abstractfactory2.factory;

import com.cl.learn.factorypattern.abstractfactory2.product.Airplane;
import com.cl.learn.factorypattern.abstractfactory2.product.AirplaneHonda;
import com.cl.learn.factorypattern.abstractfactory2.product.Car;
import com.cl.learn.factorypattern.abstractfactory2.product.CarHonda;

/**
 * @Author l
 * @Date 2022/3/19 21:36
 */
public class FactoryHonda implements AbstractFactory{
    @Override
    public Car createCar() {
        return new CarHonda();
    }

    @Override
    public Airplane createAirplane() {
        return new AirplaneHonda();
    }
}
