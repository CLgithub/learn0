package com.cl.learn.myspring.spring;

import java.lang.reflect.Type;

/**
 * @Author l
 * @Date 2022/3/26 22:53
 */
public class BeanDefinition {
    private Class type;
    private Scope scope;

    public BeanDefinition(Class type, Scope scope) {
        this.type = type;
        this.scope = scope;
    }

    public BeanDefinition() {
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "type=" + type +
                ", scope=" + scope +
                '}';
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
