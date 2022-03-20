package com.cl.learn.factorypattern.factorymethod;

import com.cl.learn.factorypattern.ordinary.Operation;
import com.cl.learn.factorypattern.ordinary.OperationAdd;

/**
 * @Author l
 * @Date 2022/3/18 18:02
 */
public class OperationFactoryAdd implements OperationFactory{
    public Operation createOperation() {
        return new OperationAdd();
    }
}
