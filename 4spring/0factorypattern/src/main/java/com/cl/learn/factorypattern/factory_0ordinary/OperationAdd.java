package com.cl.learn.factorypattern.factory_0ordinary;

/**
 * @Author l
 * @Date 2022/3/18 17:19
 */
//加法
public class OperationAdd extends Operation {
    @Override
    public double getResule() {
        return getValue1() + getValue2();
    }
}
