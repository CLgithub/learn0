package com.cl.learn.myspring.spring;

import com.cl.learn.myspring.spring.annotation.Autowird;
import com.cl.learn.myspring.spring.annotation.Component;
import com.cl.learn.myspring.spring.annotation.ComponentScan;
import com.cl.learn.myspring.spring.annotation.Scan;
import com.sun.deploy.util.StringUtils;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author l
 * @Date 2022/3/26 11:56
 */
public class MySpringApplicationContext {
    private Class configClass;
    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(); //
    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();   // 单例池


    public MySpringApplicationContext(Class configClass) {
        this.configClass=configClass;
        // 扫描
        if(this.configClass.isAnnotationPresent(ComponentScan.class)){
            ClassLoader classLoader = this.getClass().getClassLoader();
            String classPath = classLoader.getResource("").getPath(); // 类路径

            ComponentScan componentScan = (ComponentScan) this.configClass.getAnnotation(ComponentScan.class);
            String packageScan = componentScan.value(); // com.cl.learn.myspring.demo
            String canonicalPath = packageScan.replaceAll("\\.", "/"); // 相对路径 com/cl/learn/myspring/demo

//            URL resource = classLoader.getResource("./" + canonicalPath);
            File classAbsolutePath = new File(classPath+canonicalPath); // 类路径+相对路径
            if(classAbsolutePath.isDirectory()){
                File[] classFiles = classAbsolutePath.listFiles();
                for (File classFile : classFiles) {
                    String filePath = classFile.getAbsolutePath();
                    String className = filePath.substring(classPath.length(), filePath.indexOf(".class")).replaceAll("\\/", "\\.");
                    try {
                        Class<?> class1 = classLoader.loadClass(className);
                        if(class1.isAnnotationPresent(Component.class)){ // 如果一个类上有Component注解

                            Component component = class1.getAnnotation(Component.class);
                            String beanName= component.value();
                            if("".equals(beanName)){
                                beanName = class1.getSimpleName();
                                beanName = Introspector.decapitalize(beanName); //首字母小写
                            }

                            // 则说明定义了一个bean BeanDefinition
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setType(class1);
                            Scan scan = class1.getAnnotation(Scan.class);
                            if(scan == null)
                               beanDefinition.setScope(Scope.singleton);    // 默认单例子
                            else
                                beanDefinition.setScope(scan.value());

                            String simpleName = class1.getSimpleName();
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        // 生成单例bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            Scope scope = beanDefinition.getScope();
            if(Scope.singleton == scope){ // 如果是单例
                // 创建bean
                Object bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            // Object o = clazz.newInstance();
            // 实例化bean
            Object bean = clazz.getConstructor().newInstance(); // 得到无参构造方法，并创建对象
            // 对bean进行依赖注入
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field: declaredFields) {
                if(field.isAnnotationPresent(Autowird.class)){   // 如果属性有Autowird 注解
                    field.setAccessible(true);
                    field.set(bean,getBean(field.getName()));
                }
            }
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition==null){
            throw new NullPointerException();
        }
        Scope scope = beanDefinition.getScope();
        if(scope == Scope.singleton){ // 如果是单例
            Object bean = singletonObjects.get(beanName);
            if(bean==null){
                bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
            return bean;
        }else{
            return createBean(beanName, beanDefinition);
        }
    }
}
