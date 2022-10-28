package com.cl.learn.factorypattern.abstractfactory3.factory;

import com.cl.learn.factorypattern.abstractfactory3.product.AbstractProduct;
import com.cl.learn.factorypattern.abstractfactory3.product.MTCare;

import java.util.List;

/**
 * @Author l
 * @Date 2022/10/28 11:52
 */
public abstract class AbstractFactory3 {

    public static <T extends AbstractFactory3> T getFactory(Class<? extends AbstractFactory3> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    abstract <T extends AbstractProduct> T createProduct(Class<? extends AbstractProduct> clazz);
}
