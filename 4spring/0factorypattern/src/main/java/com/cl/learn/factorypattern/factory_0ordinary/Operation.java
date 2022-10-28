package com.cl.learn.factorypattern.factory_0ordinary;


public abstract class Operation {
    private double value1 = 0;
    private double value2 = 0;
    public abstract double getResule() throws IllegalAccessException;

    public Operation(double value1, double value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public Operation(){}



    public double getValue1() {
        return value1;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }
}


