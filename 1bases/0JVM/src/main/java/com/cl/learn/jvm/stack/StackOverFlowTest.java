package com.cl.learn.jvm.stack;

import org.nd4j.shade.jackson.core.JsonProcessingException;

import java.util.StringJoiner;

/**
 * 栈内存溢出：stackOverFlowError
 *      栈帧太大:两个对象相互引用，toString时
 */
public class StackOverFlowTest {
    public static void main(String[] args) throws JsonProcessingException {
        B b = new B();
        A a = new A();
        a.setB(b);
        b.setA(a);
        System.out.println(a);

    }
}

class A{
    private B b;


    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("A{");
        sb.append("b=").append(b);
        sb.append('}');
        return sb.toString();
    }
}
class B{
    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("B{");
        sb.append("a=").append(a);
        sb.append('}');
        return sb.toString();
    }
}
