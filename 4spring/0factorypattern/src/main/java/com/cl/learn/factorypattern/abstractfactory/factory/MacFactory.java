package com.cl.learn.factorypattern.abstractfactory.factory;

import com.cl.learn.factorypattern.abstractfactory.product.Keyboard;
import com.cl.learn.factorypattern.abstractfactory.product.MacMouse;
import com.cl.learn.factorypattern.abstractfactory.product.Mouse;
import com.cl.learn.factorypattern.abstractfactory.product.MacKeyboard;

/**
 * @Author l
 * @Date 2022/3/19 21:16
 */
public class MacFactory implements Hardware {
    @Override
    public Keyboard createKeyBoard() {
        return new MacKeyboard();
    }

    @Override
    public Mouse createMouse() {
        return new MacMouse();
    }
}
