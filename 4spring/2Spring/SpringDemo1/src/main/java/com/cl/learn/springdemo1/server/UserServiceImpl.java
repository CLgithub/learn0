package com.cl.learn.springdemo1.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;

/**
 * @Author l
 * @Date 2022/5/17 11:12
 */
@Service("userService")
public class UserServiceImpl implements UserService
        , InitializingBean // 属性设置前逻辑 初始化
        , BeanNameAware // aware机制
        , BeanFactoryAware
        , BeanClassLoaderAware
{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**********************************************/
    @PostConstruct  // post构建器
    public void testPostConstrut(){
        System.out.println("构造。。。");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("属性设置前 业务逻辑、初始化");
    }


    /**********************************************/
    @Override
    @Transactional // 在需要开启事务的方法标注
    public void insertUser() {
        System.out.println("正常业务逻辑");
        jdbcTemplate.execute("insert into user values('小明',23)");
//        throw new RuntimeException();
    }



    /***************** Aware 机制 从spring获取某些东西 **********************/
    @Override
    public void setBeanName(String beanName) {
        System.out.println("Aware机制，从spring中获取到beanName："+beanName);
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        System.out.println(beanFactory);
    }
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
//        System.out.println(classLoader);
    }

}
