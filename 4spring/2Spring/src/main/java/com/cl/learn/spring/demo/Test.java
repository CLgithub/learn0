package com.cl.learn.spring.demo;

import com.cl.learn.spring.demo.config.Appconfig;
import com.cl.learn.spring.demo.service.A;
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
            // 1.事务管理器新建一个数据库连接conn1 并存放在 ThreadLocal<Map<DataSource,Connection>> 中
            // 2.conn1.autocommit = false

            // target.test();   // 普通对象.test() 在ThreadLocal中去找conn1，利用conn1来执行sql

            // 方法执行完 若无异常 conn1.commit() 若有异常 conn1.rollback()
         }
     }

 ThreadLocal<Map<DataSource,Connection>> 当不加@Configuration注解时，DataSource就是多例的，
    事务管理器 和 jdbcTemplate 得到的数据源就非同一个对象，jdbcTemplate要执行sql，在 ThreadLocal 中利用DataSource去查找conn1时就找不到
    就会自己去创建Connection，此连接执行后就立马提交，所以抛异常也没有回滚


 @Configuration AOP @Lazy
 都是基于动态代理

 循环依赖：
    1、构建A对象
        在单例池中查找A--->没有找到--->A.class--->实例化得到A普通对象
    2、依赖注入B
        2.1、在单例池中查找B--->没有找到--->B.class--->实例化得到B普通对象
        2.2、依赖注入A
            在单例池中查找A--->没有找到--->A.class--->实例化得到A普通对象
            变成循环依赖♻️
        2.3、属性填充
        2.4、做一些其他事（AOP）
        2.5、添加到singletonObjects 单例池Map<beanName,bean>
    3、属性填充
    4、做一些其他事（AOP）
    5、添加到singletonObjects 单例池Map<beanName,bean>




 spring如何解决循环依赖：
    spring中有三级缓存，其实就是3个Map
    第1级：singletonObjects        单例池
    第2级：earlySingletonObjects   早期的单例池
    第3级：singletonFactories      单例工厂池

 加入缓存后
     1、构建A对象
        在单例池中查找A没有找到---->在clMap.get('A')没有找到--->A.class实例化得到A普通对象--->存入缓存Map clMap.put("A",A普通对象)
     2、依赖注入B
         2.1、在单例池中查找B没有找到--->在clMap.get('B')没有找到--->B.class实例化得到B普通对象--->存入缓存Map clMap.put("B",B普通对象)
         2.2、依赖注入A
             在单例池中查找A没有找到--->在clMap.get('A')得到普通对象A（关键）
         2.3、属性其他填充
         2.4、做一些其他事（AOP）
         2.5、添加到singletonObjects 单例池Map<beanName,bean> Map<'B',B代理对象>
     3、属性其他填充
     4、做一些其他事（AOP）--->A代理对象
     5、添加到singletonObjects 单例池Map<beanName,bean>  Map<'A',A普通对象>
 核心：就是先存起来




 但也存在问题，只存入普通对象
 解决办法：若循环依赖，就提前进行AOP，若不存在，就在4再执行AOP


 1 构建a
    依次在1 2 3 级缓存中找不到--->A.class实例化得到a( b=null, ... )--->存入3级缓存 singletonFactories.put('a', ()->getEarlyBeanReference( 'a', mbd, a(b=null,...) ))
 2 依赖注入b 此时a( b=null, ... )
    2.1 构建b
        2.1.1 依次在1 2 3 级缓存中找不到--->B.class实例化得到b( a=null, ... )--->存入3级缓存 singletonFactories.put('b', ()->getEarlyBeanReference( 'b', mbd, b(a=null,...) ))
        2.1.2 依赖注入a 此时b( a=null, ... )
            2.1.2.1 构建a
                依次在1 2 3 级缓存中找--->在3级缓存中找到singletonFactories.get('a')，执行getEarlyBeanReference( 'a', mbd, a(b=null,...) )
                    得到a( b=null, ... )，并创建ap( target=a(b=null,...),... )
                    发现A中出现了循环依赖
                        a ap( target=a,... ), 存a到earlyProxyReferences 返回ap
                        将a从3级缓存移入2级缓存 earlySingletonObjects.put( 'a', ap(target=a(b=b,...)) )
            2.1.2.2 填充a
                b( a=ap, ...)

        2.1.3 依赖注入其他属性
        2.1.4 做一些其他事(AOP) 得到bp(target=b)
        2.1.5
    2.2 填充b
        ap( b=b(a=ap) )
 3 依赖注入其他属性
 4 做一些其他事(初始化前\初始化\初始化后\ AOP产生代理对象)，
    if(earlyProxyReferences.remove(cacheKey) != bean){
        return ap
    }
    如果在earlyProxyReferences 中能取出a，说明之前已经进行了AOP，a中出现了循环依赖，ap已经在2级中，返回a
    如果没有取出a，就去进行AOP，返回ap
 4.5 将ap从2级缓存移入1级缓存
 5 移动到1级缓存单例池中 Map<beanName,bean> Map<'a',ap>






 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext(Appconfig.class);
        A a = (A) annotationConfigApplicationContext.getBean("a");
        a.test();

        UserService userService = (UserService) annotationConfigApplicationContext.getBean("userService");  // Map<BeanName, Bean>  //单例池
        UserService userService2 = (UserService) annotationConfigApplicationContext.getBean("userService");
        System.out.println(userService);
        System.out.println(userService2);
//        userService.test2();
        userService.test();
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
