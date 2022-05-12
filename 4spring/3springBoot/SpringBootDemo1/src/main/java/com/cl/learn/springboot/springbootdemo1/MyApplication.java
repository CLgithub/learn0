package com.cl.learn.springboot.springbootdemo1;

import com.cl.learn.springboot.other.MyConfig;
import com.cl.learn.springboot.springbootdemo1.entity.Order;
import com.cl.learn.springboot.springbootdemo1.service.OrderService;
import com.cl.learn.springboot.springbootdemo1.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @Author l
 * @Date 2022/5/9 18:05
 */
@MapperScan("com.cl.learn.springboot.springbootdemo1.mapper")
//@SpringBootApplication
@SpringBootConfiguration // 告诉spring这是一个配置Bean
@EnableAutoConfiguration // 启用自动配置
@ComponentScan(          // 扫描
        excludeFilters = {  // 排除过滤器
                @ComponentScan.Filter( type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
                // 扫描时排除掉：类型为TypeExcludeFilter的，若不想让某类被扫描，可自定义一个类，继承TypeExcludeFilter，并重写其中match方法，
                // 进行自定义排除，TypeExcludeFilter也是被排除的
                // 但具体细节如下：在初始化时，就要先去注册MyType，不要等到自动扫描，MyType中进行排除逻辑，等到扫描时就可以排除掉
                @ComponentScan.Filter( type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)
                // 扫描时排除掉：有@Configuration 并且 是 org.springframework.boot.autoconfigure.EnableAutoConfiguration 类型的
                // 对于这样的在spring.factories中加载就行
        })
public class MyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MyApplication.class, args);
        Order bean = applicationContext.getBean(Order.class);
        System.out.println(bean);
        Order bean2 = applicationContext.getBean(Order.class);
        System.out.println(bean2);
        MyConfig bean1 = applicationContext.getBean(MyConfig.class);
        System.out.println(bean1);
//        MyType bean3 = applicationContext.getBean(MyType.class);
//        System.out.println(bean3);
        OrderService bean3 = applicationContext.getBean(OrderService.class);
        System.out.println(bean3);
    }

}