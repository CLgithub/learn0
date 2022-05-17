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

`引入starter--->自带META-INF/spring.factories--->引导加载自己的xxxAutoConfiguration--->其中很多条件判断--->引导加载相关依赖--->配置/绑定属性--->整合了xxx`

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
    
    loadSpringFactories，会加载类路径中的spring.factories文件，spring.factories共有3个，分别
    * spring-boot.jar/META-INF/spring.factories
    * spring-beans.jar/META-INF/spring.factories
    * spring-boot-autoconfigure.jar/META-INF/spring.factories
 
    共有key13个，并缓存在一个Map中
    <img src='./images/1.png'>

### @SpringBootApplication注解
* @SpringBootConfiguration 告诉spring这是一个配置Bean
* @EnableAutoConfiguration 启用自动配置
    * 类加载器去加载类路径下`META-INF/spring.factories`中的资源（各种类型分组），并获取到其中的`AutoConfigurationImportFilter`，利用注解进行条件判断，排除不需要的，最终得到需要的bean，[具体如何做](./SpringBoot原理.md)
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

## 启动流程
1. 生成一个`SpringApplication`对象
    1. `webApplicationType = `推测应用类型（None/Servlet/Reactive）
    2. 从`spring.factories`中获取 **1、BootstrapRegistryInitializer** **2、应用容器初始化器** 和 **3、应用监听器**
    3. 推断主启动类
    ```java
    // 主要都是在给属性赋值
    public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
		// 给资源加载器赋值
		this.resourceLoader = resourceLoader;
		Assert.notNull(primarySources, "PrimarySources must not be null");
		// 启动时传入的主启动类，封装后赋值给primarySources
		this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
		// 从类路径下的类的情况，推断是否为web应用，如果是是哪种web应用（Servlet/Reactive）
		this.webApplicationType = WebApplicationType.deduceFromClasspath();
		// 下面三处，均执行getSpringFactoriesInstances方法，得到的结果放入集合，getSpringFactoriesInstances根据传入参数，获取META-INF/spring.factories中对应类型的类s，实例化后排序并返回
		this.bootstrapRegistryInitializers = new ArrayList<>(
				getSpringFactoriesInstances(BootstrapRegistryInitializer.class));
		setInitializers((Collection) 
		// 应用容器初始化器 7个
		getSpringFactoriesInstances(ApplicationContextInitializer.class));
		// 应用监听器 目前有8个
		setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
		// 推断主启动类（从内存的线程栈中去找main）
		this.mainApplicationClass = deduceMainApplicationClass();
	}
    ````
2. 执行`SpringApplication`的`run()`方法
    1. 获取运行状态监听器`SpringApplicationRunListener(EventPublishingRunListener) listener`
    2. `EventPublishingRunListener.starting()`
    3. 创建一个`Spring`容器context
    4. 准备容器prepareContext
        1. 执行初始化器的初始化方法
        2. 调用运行状态监听器`listener`的contextPrepared()方法，表示容器已经准备好了
        3. 把run方法传进来的类注册到Spring容器中，成为一个Bean
        4. 调用运行状态监听器`listener`的contextLoaded()方法，表示容器加载完成
    5. 刷新Spring容器，会解析配置类、扫描、启动webServer（AutoConfigurationImportSelector其实在此时也会去解析，AutoConfigurationImportSelector）
    6. `listeners.started(context, timeTakenToStartup);`表示启动完成
    7. `callRunners`在容器中获取`ApplicationRunner`和`CommandLineRunner`的bean，执行其中的run方法
    8. `listeners.ready(context, timeTakenToReady);`
    9. 返回context
    ```java
    public ConfigurableApplicationContext run(String... args) {
		// 记录开始时间
		long startTime = System.nanoTime(); 
		//暂时先不管
		DefaultBootstrapContext bootstrapContext = createBootstrapContext();
		// 定义spring容器	
		ConfigurableApplicationContext context = null; 
		configureHeadlessProperty();
		// 获取运行状态监听器（在spring.factories中去获取）得到EventPublishingRunListener，它会在启动过程的各个阶段，发布对应的事件
		SpringApplicationRunListeners listeners = getRunListeners(args);
		// 监听主启动类的状态为「启动中」时（背后去调「启动中」对应的回调函数）
		listeners.starting(bootstrapContext, this.mainApplicationClass);
		try {
			// 封装run方法的参数为一个对象
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
			// 准备环境，封装到environment中
			ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments);
			// 暂时不管 配置bean信息
			configureIgnoreBeanInfo(environment);
			// 打印图标
			Banner printedBanner = printBanner(environment);
			// 创建context，函数式接口，根据应用类型this.webApplicationType，创建不同的spring容器context
			context = createApplicationContext();
			// 暂时不管
			context.setApplicationStartup(this.applicationStartup);
			// 1 执行初始化器的初始化方法
			// 2 调用运行状态监听器的contextPrepared()方法，表示容器已经准备好了
			// 3 把run方法传进来的类注册到Spring容器中，成为一个Bean
			// 4 调用运行状态监听器的contextLoaded()方法，表示容器加载完成
			prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
			// 刷新Spring容器，会解析配置类、扫描、启动webServer
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime);
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), timeTakenToStartup);
			}
			listeners.started(context, timeTakenToStartup);
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, listeners);
			throw new IllegalStateException(ex);
		}
		try {
			Duration timeTakenToReady = Duration.ofNanos(System.nanoTime() - startTime); // 得到用了多少时间
			listeners.ready(context, timeTakenToReady);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}
    ```

SpringApplication.run(Application.class,args);
1. 创建SpringApplication应用
    1. 推断应用类型，web/非web，（根据类路径中有哪些类）
    2. 加载 MATE-INF/spring.factories 中的 初始化器 和 监听器
    3. 推断主启动类
2. 调用SpringApplication.run方法
    1. 创建SpringContext，并调用初始化器对容器进行初始化
    2. 刷新容器
        1. 解析配置类
        2. 扫描和解析@Bean
        3. 加载 MATE-INF/spring.factories 中的自动配置（启动Tomcat，如果有，Tomcat依赖从自动配置而来）
    3. 执行callRunner
    4. 返回
    
2.2.4 启动Tomcat
```java
MATE-INF/spring.factories--->ServletWebServerFactoryAutoConfiguration--->ServletWebServerFactoryCustomizer自定义器 和WebServerFactoryCustomizerBeanPostProcessor 初始化前处理器
SpringApplication.run(){
    ...
    refreshContext(context);
        ...
        ServletWebServerApplicationContext.createWebServer(){
            // 会调用WebServerFactoryCustomizerBeanPostProcessor bean初始化前处理器，修改参数
            ServletWebServerFactory factory = getWebServerFactory();
            this.webServer = factory.getWebServer(getSelfInitializer());
                ...
                TomcatServletWebServerFactory.getWebServer(ServletContextInitializer... initializers) {
                    // 创建
                    Tomcat tomcat = new Tomcat();
                    // 设置参数
                    customizeConnector(connector);
                        void customizeConnector(Connector connector) {
                            int port = Math.max(getPort(), 0);
                                AbstractConfigurableWebServerFactory.port = 8080;
                            connector.setPort(port);
                            ...
                        }
                    tomcat.setConnector(connector);
                    ...
                    //返回并启动
                    return getTomcatWebServer(tomcat);
                        ...
                        TomcatWebServer(Tomcat tomcat, boolean autoStart, Shutdown shutdown){
                            ...
                            private void initialize() throws WebServerException {
                                ...
                                // Start the server to trigger initialization listeners
                                this.tomcat.start(); //并不会去阻塞
				                ...
				                // 开启另外一个线程，去while(true)阻塞接收请求
                            }
                        }
                }
        }
}   
```


若在`application.properties`中设置了`server.port=8081`会被属性绑定到`ServerProperties`中，`ServletWebServerFactoryAutoConfiguration`中`@Bean`定义了`ServletWebServerFactoryCustomizer`
在`ServletWebServerFactoryCustomizer`自定义器的自定义方法
```java
public class ServletWebServerFactoryCustomizer
		implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>, Ordered {
	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		map.from(this.serverProperties::getPort).to(factory::setPort);
		map.from(this.serverProperties::getAddress).to(factory::setAddress);
        map.from(this.serverProperties.getServlet()::getContextPath).to(factory::setContextPath);
        ...
    }
}
```
`factory.setPort` 实现至`interface ConfigurableWebServerFactory.setPort`
`AbstractConfigurableWebServerFactory.port`也是
何时调用customize方法?
```java
// 实现了BeanPostProcessor接口，创建bean初始化前处理器
// 哪个bean？WebServerFactory（TomcatServletWebServerFactory）
public class WebServerFactoryCustomizerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    ...
    ...
    // bean初始化前，敢一些事，调用WebServerFactoryCustomizer自定义器的customize方法
    private void postProcessBeforeInitialization(WebServerFactory webServerFactory) {
		LambdaSafe.callbacks(WebServerFactoryCustomizer.class, getCustomizers(), webServerFactory)
				.withLogger(WebServerFactoryCustomizerBeanPostProcessor.class)
				.invoke((customizer) -> customizer.customize(webServerFactory));
	}
}
```
