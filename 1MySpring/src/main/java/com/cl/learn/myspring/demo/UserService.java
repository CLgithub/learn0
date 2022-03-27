package com.cl.learn.myspring.demo;

import com.cl.learn.myspring.spring.BeanNameAware;
import com.cl.learn.myspring.spring.InitializingBean;
import com.cl.learn.myspring.spring.Scope;
import com.cl.learn.myspring.spring.annotation.Autowird;
import com.cl.learn.myspring.spring.annotation.Component;
import com.cl.learn.myspring.spring.annotation.ComponentScan;
import com.cl.learn.myspring.spring.annotation.Scan;

/**
 * @Author l
 * @Date 2022/3/26 11:47
 */
@Component
@Scan(Scope.prototype)
public class UserService implements BeanNameAware, InitializingBean {

    @Autowird
    private OrderService orderService;

    private String beanName;

    public void test(){
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName=beanName;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "orderService=" + orderService +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("init");
    }
}
