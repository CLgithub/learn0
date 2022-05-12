# SpringBoot 学习

Spring中，手动引入依赖、手动配置
SpringBoot，自动引入依赖，自动配置

* `spring-boot-starter-parent`
    ```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.6</version>
    </parent>
    ```
    * 其父工程`spring-boot-dependencies`
        ```xml
      <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>2.6.6</version>
      </parent>
        ```
        其`<dependencyManagement/>`中：
        * 声明了很多常用依赖版本，如`activemq`、`kafka`、`redis`等
        * 以及starter版本，如`spring-boot-starter-web`、`spring-boot-starter-activemq`、`spring-boot-starter-data-redis`等

        **`<dependencyManagement/>`只是声明，统一管理版本，并不实际引入`jar`，子工程可不写版本号，`<dependencies/>`才会实际引入`jar`**


    
### starter

官方starter命名`spring-boot-starter-xxx`
第三方starter命名`xxx-spring-boot-starter`

引入starter后，相关依赖也会自动引入，不需要自己再去查找引入

### @Configuration
作用：告诉spring这是一个配置Bean
```java
public @interface Configuration {
    @AliasFor(
        annotation = Component.class
    )
    String value() default "";
    // proxyBeanMethods默认为true，表示@Configuration修饰的配置Bean中，在执行@Bean修饰的方法时，是走代理的逻辑（从spring容器中获取）否直接执行
    // 一般都是单例bean，@Bean方法在配置类中相互调用时，应该想要得到的是同一个对象，所以默认为true
    boolean proxyBeanMethods() default true;
}   
```

* @ImportResource("spring.xml") //导入资源
* @Import(Order.class)    // 导入bean
* 自动配置
    SpringFactoriesLoader中，会使用类加载器去加载类路径下`META-INF/spring.factories`中的资源（各种类型分组），利用注解进行条件判断，排除不需要的，最终得到需要的bean 
### @SpringBootApplication注解
* SpringBootConfiguration 告诉spring这是一个配置Bean
* EnableAutoConfiguration 启用自动配置
    * 类加载器去加载类路径下`META-INF/spring.factories`中的资源（各种类型分组），利用注解进行条件判断，排除不需要的，最终得到需要的bean
* ComponentScan 扫描