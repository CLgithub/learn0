package com.cl.learn.factorypattern.abstractfactory.factory;

import com.cl.learn.factorypattern.abstractfactory.product.Keyboard;
import com.cl.learn.factorypattern.abstractfactory.product.Mouse;

/**
 *
 * 抽象工厂模式：在工厂方法模式的基础上，将一系列产品放到同一个子实现类中去进行
 *
 */
public interface Hardware {
    Keyboard createKeyBoard();
    Mouse createMouse();
}
