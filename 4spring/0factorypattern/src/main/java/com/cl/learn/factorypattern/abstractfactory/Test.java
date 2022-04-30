package com.cl.learn.factorypattern.abstractfactory;

import com.cl.learn.factorypattern.abstractfactory.factory.Hardware;
import com.cl.learn.factorypattern.abstractfactory.factory.MacFactory;
import com.cl.learn.factorypattern.abstractfactory.product.Keyboard;

/**
 * @Author l
 * @Date 2022/3/19 21:02
 */
public class Test {
    public static void main(String[] args) {
        Hardware hardware=new MacFactory();
        Keyboard keyBoard = hardware.createKeyBoard();
        keyBoard.input();
    }
}
