package com.cl.learn.factorypattern.factorymethod;

import com.cl.learn.factorypattern.ordinary.Operation;
import com.cl.learn.factorypattern.ordinary.OperationSub;

/**
 * @Author l
 * @Date 2022/3/18 18:08
 */
public class OperationFactorySub implements OperationFactory{
    @Override
    public Operation createOperation() {
        return new OperationSub();
    }
}
