package com.cl.learn.inner;

/**
 * 内部类整理：
 *  1 成员内部类: 相当于外部成员的一个变量
 *  2 局部内部类
 *  3 静态内部类
 *  4 匿名内部类
 *
 *
 */
public class Outer {
    private String outerValue="outerValue";
    public class Inner{
        private String innerValue="innerValue";
        public void printInner(){
            System.out.println("访问外部类的私有属性:" + outerValue);
            System.out.println("访问内部类的私有属性:" + innerValue);
        }
    }
}
