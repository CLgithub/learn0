package com.cl.learn.spring.demo;

import com.cl.learn.spring.demo.config.Appconfig;
import com.cl.learn.spring.demo.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 bean创建的生命周期
 UserService.calss--->推断构造方法--->普通对象--->依赖注入--->初始化前(@PostConstruct)--->初始化(afterPropertiesSet)
    --->初始化后(AOP)--->代理对象(并没有依赖注入)--->放入Map(单例池)缓存起来--->bean对象

 单例多例指bean的名字，一个单例bean，SpringApplication中可能还有其他同一个类型的bean，beanName不同而已
 因为单例池的设计是 Map<beanName,bean>

 可用@Autowired指定构造方法
 若无指定，可推断构造方法:
    若只有一个，那就用这个
    若又多个，就会去找无参的那个
        若没有无参的那个，就报错

 依赖注入：
    1、加了注解@Autowired的属性
    2、给这个属性赋值
        1、这个值如果是多例的 直接创建
        2、如果是单例的
            根据属性类型和属性名共同查找
            if(==Type){
                if(只有一个){
                    就用这个
                }else{
                    if(==beanName){
                        就用它
                    }else{
                        报错
                    }
                }
            } else{
                报错
            }


 循环依赖：

 AOP
    通过CGLIB产生代理对象，在代理对象中做增强、做切面
    CGLIB 产生的代理对象是 UserService的子类 代理对象中没有依赖注入，

    CGLIB产生UserServiceProxyd代理对象(并没有依赖注入)--->UserServiceProxyd代理对象.target=UserService普通对象

    CGLIB代理
    class UserServiceProxy extends UserService{

        UserService target;

        // 代理类中会重写父类方法
        public void test(){
            // 切面逻辑@Before
            // target.test();
        }
    }

 spring事务原理
    同样得到的是代理对象
     class UserServiceProxy extends UserService{

         UserService target;

         // 代理类中会重写父类方法
         public void test(){

            // sping事务切面逻辑

            // @Transactional

            // 开启事务
            // 1.事务管理器新建一个数据库连接conn1
            // 2.conn1.autocommit = false

            // target.test();   // 普通对象.test() 利用conn1来执行sql

            // 方法执行完 若无异常 conn1.commit() 若有异常 conn1.rollback()
         }
     }






 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext(Appconfig.class);
        UserService userService = (UserService) annotationConfigApplicationContext.getBean("userService");  // Map<BeanName, Bean>  //单例池
        UserService userService2 = (UserService) annotationConfigApplicationContext.getBean("userService");
        System.out.println(userService);
        System.out.println(userService2);
        userService.test2();
//        OrderService orderService= (OrderService) annotationConfigApplicationContext.getBean("orderService");
//        orderService.test();



//
//        模拟依赖注入
//        UserService userService1 = new UserService();
//        for (Field field : userService1.getClass().getDeclaredFields()) {
//            if(field.isAnnotationPresent(Autowired.class)){
//                field.set(userService1, ??);
//            }
//        }
//
//        // 模拟初始化前
//        for (Method method: userService1.getClass().getDeclaredMethods()) {
//            if(method.isAnnotationPresent(PostConstruct.class)){
//                method.invoke(userService1, null);
//            }
//        }


    }
}
