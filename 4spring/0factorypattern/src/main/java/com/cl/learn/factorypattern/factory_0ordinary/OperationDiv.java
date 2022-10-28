package com.cl.learn.factorypattern.factory_0ordinary;

/**
 * @Author l
 * @Date 2022/3/18 17:22
 */
// 除发
public class OperationDiv extends Operation{

    @Override
    public double getResule() throws IllegalAccessException {
        if(getValue2() != 0){
            return getValue1() / getValue2();
        }
        throw new IllegalAccessException("除数不能为0");
    }
}
