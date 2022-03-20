package com.cl.learn.factorypattern.ordinary;

/**
 * @Author l
 * @Date 2022/3/18 17:20
 */
// 乘法
public class OperationMul extends Operation {
    @Override
    public double getResule() {
        return getValue1() * getValue2();
    }
}
