package com.cl.learn.myspring.demo;

import com.cl.learn.myspring.spring.BeanNameAware;
import com.cl.learn.myspring.spring.InitializingBean;
import com.cl.learn.myspring.spring.Scope;
import com.cl.learn.myspring.spring.annotation.Autowird;
import com.cl.learn.myspring.spring.annotation.Component;
import com.cl.learn.myspring.spring.annotation.Scan;

/**
 * 要实现AOP
 * 就要生成bean的代理对象，bean的原类型就必须要实现接口
 * UserService必须实现接口，
 */
@Component
public class UserService implements UserServiceInterface, BeanNameAware, InitializingBean{

    @Autowird
    private OrderService orderService;

    private String beanName;

    @Override
    public void test(){
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName=beanName;
    }

//    @Override
//    public String toString() {
//        return "UserService{" +
//                "orderService=" + orderService +
//                ", beanName='" + beanName + '\'' +
//                '}';
//    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("init");
    }
}
