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


    
## starter

官方starter命名`spring-boot-starter-xxx`
第三方starter命名`xxx-spring-boot-starter`

引入starter后，相关依赖也会自动引入，不需要自己再去查找引入

starter包含：
* 该starter所需的相关依赖
* META-INF/spring.factories
* xxxAutoConfiguration

`引入starter--->自带META-INF/spring.factories--->引导加载自己的xxxAutoConfiguration--->其中很多条件判断--->引导加载相关依赖--->整合了xxx`

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
<<<<<<< HEAD
* @SpringBootConfiguration 告诉spring这是一个配置Bean
* @EnableAutoConfiguration 启用自动配置
    * 类加载器去加载类路径下`META-INF/spring.factories`中的资源（各种类型分组），利用注解进行条件判断，排除不需要的，最终得到需要的bean，[具体如何做](./SpringBoot原理.md)
* @ComponentScan 扫描

```java
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
         SpringApplication.run(MyApplication.class, args);
    }
}
```


#### 条件判断注解
```java
@ConditionalOnBean  // 是否存在某个类或某个名字Bean
@ConditionalOnMissingBean // 是否缺失某个类或某个名字Bean
@ConditionalOnSingleCandidate // 是否符合制定类型的Bean是单例的

@ConditionalOnClass // 是否存在某个类
@ConditionalOnMissingClass // 是否缺失某个类

@ConditionalOnWebApplication // 当前应用是否是web应用
@ConditionalOnNotWebApplication // 当前应用是否非web应用
@ConditionalOnWarDeployment // 当前项目是否以War包部署方式运行

@ConditionalOnProperty // 某个属性（key）是否存在 ‼️，其中matchIfMissing属性常用

@ConditionalOnExpression // 指定的表达式返回是treu还是false，el表达式
@ConditionalOnJava // 是否是指定的java版本 某版本的某类中是否有某方法--->判定是否是某版本
@ConditionalOnJndi // JNDI指定资源是否存在
@ConditionalOnResource // 指定资源是否存在
@ConditionalOnCloudPlatform // 是否在某个云平台上 运行的操作系统环境 docker相关
```

#### 属性绑定
* @PropertySource 注解，指定配置文件
* @EnableConfigurationProperties({P.class,P2.class}) 用来指定P,P2为属性封装类，把配置中的内容绑定到P,P2中，这样设计的目的是，在满足一定条件后，再去开启属性绑定
* @ConfigurationPropertiesScan("com.properties") 将`com.properties`包下的有`@ConfigurationProperties`注解的所有类指定为属性封装类
* @ConfigurationProperties 暂时理解标记属性封装类的作用，可以设置前缀

## 环境变量
优先级：JVM(启动时参数配置) > 操作系统 > application.properties > application.yml

### Profiles文件
不同环境，不同的配置文件
```
application.properties          # 默认
application-xxx1.properties     # xxx1环境
application-xxx2.properties     # xxx2环境
```
在intellij中，可通过`Environment variables`:`spring.profiles.active=xxx1`来进行选择配置
实际部署启动时，可通过启动脚本`java -jar -D:spring.profiles.active=xxx2 SpringBootDemo4ProFiles-1.0-SNAPSHOT.jar`进行配置
=======
* SpringBootConfiguration 告诉spring这是一个配置Bean
* EnableAutoConfiguration 启用自动配置
    * 类加载器去加载类路径下`META-INF/spring.factories`中的资源（各种类型分组），利用注解进行条件判断，排除不需要的，最终得到需要的bean，[具体如何做](./SpringBoot原理.md)
* ComponentScan 扫描
>>>>>>> 867ae957c3643e29dc6084569e5727ab9c94bab6
