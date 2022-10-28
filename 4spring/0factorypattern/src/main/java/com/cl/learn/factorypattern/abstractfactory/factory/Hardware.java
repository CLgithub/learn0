package com.cl.learn.factorypattern.abstractfactory.factory;

import com.cl.learn.factorypattern.abstractfactory.product.Keyboard;
import com.cl.learn.factorypattern.abstractfactory.product.Mouse;

/**
 *
 */
public interface Hardware {
    Keyboard createKeyBoard();
    Mouse createMouse();
}
