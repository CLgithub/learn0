package com.cl.learn.factorypattern.abstractfactory3.factory;

import com.cl.learn.factorypattern.abstractfactory3.product.AbstractProduct;
import com.cl.learn.factorypattern.abstractfactory3.product.MTCare;
import com.cl.learn.factorypattern.abstractfactory3.product.Piano;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author l
 * @Date 2022/10/28 12:02
 */
public class YMHFactory extends AbstractFactory3{


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
