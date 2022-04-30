package com.cl.learn.factorypattern.abstractfactory.factory;

import com.cl.learn.factorypattern.abstractfactory.product.Keyboard;
import com.cl.learn.factorypattern.abstractfactory.product.Mouse;
import com.cl.learn.factorypattern.abstractfactory.product.WinKeyboard;
import com.cl.learn.factorypattern.abstractfactory.product.WinMouse;

/**
 * @Author l
 * @Date 2022/3/19 21:20
 */
public class WinFactory implements Hardware{
    @Override
    public Keyboard createKeyBoard() {
        return new WinKeyboard();
    }

    @Override
    public Mouse createMouse() {
        return new WinMouse();
    }
}
