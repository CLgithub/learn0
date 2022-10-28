package com.cl.learn.factorypattern.abstractfactory3.factory;

import com.cl.learn.factorypattern.abstractfactory3.product.AbstractProduct;

/**
 * @Author l
 * @Date 2022/10/28 14:25
 */
public class HondaFactory extends AbstractFactory3{

    @Override
    public <T extends AbstractProduct> T createProduct(Class<? extends AbstractProduct> clazz) {
        try {
            AbstractProduct abstractProduct = clazz.newInstance();
            return (T) abstractProduct;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
