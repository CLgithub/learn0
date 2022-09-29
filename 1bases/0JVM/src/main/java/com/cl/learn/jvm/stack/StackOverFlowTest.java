package com.cl.learn.jvm.stack;

import org.nd4j.shade.jackson.core.JsonProcessingException;
import org.nd4j.shade.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Author l
 * @Date 2022/9/29 10:56
 */
public class StackOverFlowTest {
    public static void main(String[] args) throws JsonProcessingException {
        B b = new B();
        A a1 = new A();
        a1.setB(b);
        A a2 = new A();
        a2.setB(b);

        ArrayList<A> as = new ArrayList<>();
        as.add(a1);
        as.add(a2);
        b.setList(as);


        ObjectMapper objectMapper=new ObjectMapper();
        String value = objectMapper.writeValueAsString(b);
        System.out.println(value);
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
        return new StringJoiner(", ", A.class.getSimpleName() + "[", "]")
                .add("b=" + b)
                .toString();
    }
}
class B{
    private List<A> list;

    public List<A> getList() {
        return list;
    }

    public void setList(List<A> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", B.class.getSimpleName() + "[", "]")
                .add("list=" + list)
                .toString();
    }
}
