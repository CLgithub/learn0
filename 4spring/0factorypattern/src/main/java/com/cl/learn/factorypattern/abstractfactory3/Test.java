package com.cl.learn.factorypattern.abstractfactory3;

import com.cl.learn.factorypattern.abstractfactory3.factory.AbstractFactory3;
import com.cl.learn.factorypattern.abstractfactory3.factory.YMHFactory;
import com.cl.learn.factorypattern.abstractfactory3.product.AbstractProduct;
import com.cl.learn.factorypattern.abstractfactory3.product.MTCare;
import com.cl.learn.factorypattern.abstractfactory3.product.Piano;

import java.util.List;

/**
 * 复习抽象工厂
 * @Author l
 * @Date 2022/10/28 11:27
 */
public class Test {
    public static void main(String[] args) {

        YMHFactory factory = AbstractFactory3.getFactory(YMHFactory.class);


        AbstractProduct product = factory.createProduct(Piano.class);
        AbstractProduct product1 = factory.createProduct(MTCare.class);
        product.run();
        product1.run();


    }
}
