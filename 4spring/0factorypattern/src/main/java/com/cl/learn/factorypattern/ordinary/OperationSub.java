package com.cl.learn.factorypattern.ordinary;

/**
 * @Author l
 * @Date 2022/3/18 17:21
 */
// 减法
public class OperationSub extends Operation{
    @Override
    public double getResule() {
        return getValue1()-getValue2();
    }
}
