package com.cl.learn.factorypattern.abstractfactory.product;

/**
 * @Author l
 * @Date 2022/3/19 21:12
 */
public class MacKeyboard implements Keyboard {

    @Override
    public void input() {
        System.out.println("Mac 键盘");
    }
}
