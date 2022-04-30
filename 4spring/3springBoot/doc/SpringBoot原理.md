# SpringBoot原理



以Spring-boot 2.3.4.RELEASE为例，对应的spring版本是5.2.9RELEASE，在pom中引入web启动器

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
	<version>2.3.4.RELEASE</version>
</dependency>
```

## @SpringBootApplication

1. @SpringBootConfiguration

2. @EnableAutoConfiguration

   1. @AutoConfigurationPackage 	//添加该注解的类所在包 作为 自动配置 包 进行管理

   2. @Import(AutoConfigurationImportSelector.class)

      1. @Import 将AutoConfigurationImportSelector.class交给spring管理

      2. AutoConfigurationImportSelector.class

         1. AutoConfigurationImportSelector实现DeferredImportSelector，继承 ImportSelector，ImportSelector 根据条件动态的导入配置类（@Configuration修饰的类）[参考](https://juejin.cn/post/6844903925242396686)

            1. selectImports是AutoConfigurationImportSelector的核心

               1. ```java
                  // annotationMetadata 注解的元数据 此处为主启动类Application的注解@SpringBootApplication的元数据	
                  @Override
                  public String[] selectImports(AnnotationMetadata annotationMetadata) { 
                    if (!isEnabled(annotationMetadata)) {
                      return NO_IMPORTS;
                    }
                    // 获取AutoConfigurationEntry，其中包括List<String> configurations;和Set<String> exclusions;，获取方式看下方1
                    AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(annotationMetadata);
                    // 返回List<String> configurations 并转换为String[]
                    return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
                  }
                  ```

                  1. ```java
                     protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
                       if (!isEnabled(annotationMetadata)) {
                         return EMPTY_ENTRY;
                       }
                       // 将@EnableAutoConfiguration注解的属性装入map并返回 得到：<exclude,[]>,<excludeName,[]>，exclude排除，attributes Map装载了被排除的类
                       AnnotationAttributes attributes = getAttributes(annotationMetadata);
                       // 1.得到所有候选配置类路径（127个）得到127个EnableAutoConfiguration
                       List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
                       // 利用set取重复
                       configurations = removeDuplicates(configurations);
                       // 将排除的 类路径 变成一个Set，以及将配置在spring.autoconfigure.exclude文件中的路径加入排除Set
                       Set<String> exclusions = getExclusions(annotationMetadata, attributes);
                       // 排除
                       checkExcludedClasses(configurations, exclusions);
                       // 做具体的排除
                       configurations.removeAll(exclusions);
                       // 过滤需要的，得到23项
                       configurations = getConfigurationClassFilter().filter(configurations);
                       fireAutoConfigurationImportEvents(configurations, exclusions);
                       return new AutoConfigurationEntry(configurations, exclusions);
                     }
                     ```

                     1. 得到所有候选配置类路径（127个）

                        ```java
                        // AnnotationMetadata metadata和AnnotationAttributes attributes两个参数预留，在此处没用，要获取所有候选配置类路径，只需要classLoader
                        protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
                        		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(
                              getSpringFactoriesLoaderFactoryClass(),getBeanClassLoader()
                            );
                          	Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
                        		return configurations;
                        	}
                        ```

                        1. ```java
                           // 第一次调用loadFactoryNames，factoryTypeName=org.springframework.boot.autoconfigure.EnableAutoConfiguration
                           public static List<String> loadFactoryNames(Class<?> factoryType,@Nullable ClassLoader classLoader){
                             //  factoryType=EnableAutoConfiguration.class，factoryTypeName=org.springframework.boot.autoconfigure.EnableAutoConfiguration
                             String factoryTypeName = factoryType.getName();
                             // loadSpringFactories返回 格式为：Map<String, List<String>> <org.springframework.boot.autoconfigure.EnableAutoConfiguration = org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration, org.springframework.boot.autoconfigure.aop.AopAutoConfiguration, ...> ，
                             // 所有该方法最终返回org.springframework.boot.autoconfigure.EnableAutoConfiguration后的127个类路径
                             return loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
                           }
                           ```

                           1. ```java
                              // 最终去加载META-INF/spring.factories的地方，只需要classLoader
                              private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
                                // 先缓存在一个 Map<String, List<String>> 
                                MultiValueMap<String, String> result = cache.get(classLoader);
                                // 如果Map中有
                                if (result != null) {
                                	//取Map中的 	
                                  return result;
                                }
                              	// 使用类加载器classLoader 加载META-INF/spring.factories，并保存在result中
                                 try {
                                    ....
                                    return result;
                                 } catch (IOException ex) {
                                    throw new ...);
                                 }
                              }
                              ```

                     2. 过滤需要的

                        1. 获取过滤器

                           1. getConfigurationClassFilter

                              ```java
                              // ConfigurationClassFilter 包含 AutoConfigurationMetadata autoConfigurationMetadata 和 List<AutoConfigurationImportFilter> filters
                              private ConfigurationClassFilter getConfigurationClassFilter() {
                                 if (this.configurationClassFilter == null) {
                                    List<AutoConfigurationImportFilter> filters = getAutoConfigurationImportFilters(); //获取filters
                                    for (AutoConfigurationImportFilter filter : filters) {
                                       invokeAwareMethods(filter);	// 对过滤器AutoConfigurationImportFilter进行invokeAwareMethods
                                    }
                                    this.configurationClassFilter = new ConfigurationClassFilter(this.beanClassLoader, filters);	// 通过AutoConfigurationMetadataLoader、classLoader得到AutoConfigurationMetadata autoConfigurationMetadata
                                 }
                                 return this.configurationClassFilter;
                              }
                              // boolean[] match = filter.match(candidates, this.autoConfigurationMetadata);
                              // filter就是AutoConfigurationImportFilter接口，有一个match方法，在3个实现类中实现
                              // candidates 待进行匹配判断的配置的 String[]
                              // this.autoConfigurationMetadata = AutoConfigurationMetadataLoader.loadMetadata(classLoader);
                              ```

                              1. 获取filters

                                 1. ```java
                                    // 获取List<AutoConfigurationImportFilter>
                                    // factoryType=AutoConfigurationImportFilter.class
                                    public static <T> List<T> loadFactories(Class<T> factoryType, @Nullable ClassLoader classLoader) {
                                      Assert.notNull(factoryType, "'factoryType' must not be null");
                                      ClassLoader classLoaderToUse = classLoader;
                                      if (classLoaderToUse == null) {
                                        classLoaderToUse = SpringFactoriesLoader.class.getClassLoader();
                                      }
                                      List<String> factoryImplementationNames = loadFactoryNames(factoryType, classLoaderToUse); //第二次调用loadFactoryNames，得到3个AutoConfigurationImportFilter
                                      if (logger.isTraceEnabled()) {
                                        logger.trace("Loaded [" + factoryType.getName() + "] names: " + factoryImplementationNames);
                                      }
                                      List<T> result = new ArrayList<>(factoryImplementationNames.size());
                                      for (String factoryImplementationName : factoryImplementationNames) {
                                        result.add(instantiateFactory(factoryImplementationName, factoryType, classLoaderToUse)); //2.instantiateFactory实例化工厂，并添加到result中
                                      }
                                      AnnotationAwareOrderComparator.sort(result);
                                      return result;
                                    }
                                    ```

                                    1. 第二次调用loadFactoryNames

                                       ```java
                                       // 第二次调用loadFactoryNames，factoryTypeName=org.springframework.boot.autoconfigure.AutoConfigurationImportFilter
                                       public static List<String> loadFactoryNames(Class<?> factoryType,@Nullable ClassLoader classLoader){
                                         //  factoryType=AutoConfigurationImportFilter.class，factoryTypeName=org.springframework.boot.autoconfigure.AutoConfigurationImportFilter
                                         String factoryTypeName = factoryType.getName();
                                         return loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
                                         // 最终得到META-INF/spring.factories文件中的org.springframework.boot.autoconfigure.AutoConfigurationImportFilter 为key的值，即
                                       
                                       /**
                                       org.springframework.boot.autoconfigure.AutoConfigurationImportFilter=
                                       	org.springframework.boot.autoconfigure.condition.OnBeanCondition,
                                       	org.springframework.boot.autoconfigure.condition.OnClassCondition,
                                       	org.springframework.boot.autoconfigure.condition.OnWebApplicationCondition
                                       **/
                                       }
                                       ```

                                    2. instantiateFactory实例化工厂

                                       ```java
                                       /**
                                       String factoryImplementationName 分别是org.springframework.boot.autoconfigure.condition.OnBeanCondition,
                                       org.springframework.boot.autoconfigure.condition.OnClassCondition,
                                       org.springframework.boot.autoconfigure.condition.OnWebApplicationCondition
                                       
                                       Class<T> factoryType = AutoConfigurationImportFilter.class
                                       **/
                                       @SuppressWarnings("unchecked")
                                       private static <T> T instantiateFactory(String factoryImplementationName, Class<T> factoryType, ClassLoader classLoader) {
                                         try {
                                           // 得到OnBeanCondition.class
                                           Class<?> factoryImplementationClass = ClassUtils.forName(factoryImplementationName, classLoader);
                                           // 如果得到OnBeanCondition是AutoConfigurationImportFilter的实现类
                                           if (!factoryType.isAssignableFrom(factoryImplementationClass)) {
                                           	throw new IllegalArgumentException(
                                           "Class [" + factoryImplementationName + "] is not assignable to factory type [" + factoryType.getName() + "]");
                                           }
                                           // 分别返回OnBeanCondition,OnClassCondition,OnWebApplicationCondition3个的实例化对象
                                           return (T) ReflectionUtils.accessibleConstructor(factoryImplementationClass).newInstance();
                                         }
                                         catch (Throwable ex) {
                                           throw new IllegalArgumentException(
                                           	"Unable to instantiate factory class [" + factoryImplementationName + "] for factory type [" + factoryType.getName() + "]",
                                           ex);
                                         }
                                       }
                                       ```

                                       

                              2. 对过滤器AutoConfigurationImportFilter进行invokeAwareMethods

                                 1. ```java
                                    // instance 分别是OnBeanCondition,OnClassCondition,OnWebApplicationCondition3个的实例化对象
                                    // 三者都继承FilteringSpringBootCondition，
                                    // class FilteringSpringBootCondition extends SpringBootCondition implements AutoConfigurationImportFilter, BeanFactoryAware, BeanClassLoaderAware
                                    // 对3者设置beanClassLoader、beanFactory
                                    private void invokeAwareMethods(Object instance) {
                                      if (instance instanceof Aware) {
                                        if (instance instanceof BeanClassLoaderAware) {
                                          ((BeanClassLoaderAware) instance).setBeanClassLoader(this.beanClassLoader);
                                        }
                                        if (instance instanceof ) {
                                          ((BeanFactoryAware) instance).setBeanFactory(this.beanFactory);
                                        }
                                        if (instance instanceof EnvironmentAware) {
                                          ((EnvironmentAware) instance).setEnvironment(this.environment);
                                        }
                                        if (instance instanceof ResourceLoaderAware) {
                                          ((ResourceLoaderAware) instance).setResourceLoader(this.resourceLoader);
                                        }
                                      }
                                    }
                                    ```

                                    

                              3. 通过AutoConfigurationMetadataLoader、classLoader得到AutoConfigurationMetadata autoConfigurationMetadata

                                 1. ```java
                                    autoConfigurationMetadata = AutoConfigurationMetadataLoader.loadMetadata(classLoader);
                                    // AutoConfigurationMetadataLoader 用于加载 AutoConfigurationMetadata的工具
                                    // AutoConfigurationMetadata 提供 对自动配置注释处理器 写入的 元数据 的访问。相当于用它可以访问到自动注解的元数据
                                    
                                    // PATH = META-INF/spring-autoconfigure-metadata.properties Spring-自动配置-元数据.配置文件
                                    static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader) {
                                      return loadMetadata(classLoader, PATH);
                                    }
                                    // 将文件META-INF/spring-autoconfigure-metadata.properties 中的764项配置加载到AutoConfigurationMetadata中，Properties形式，PropertiesAutoConfigurationMetadata实现类
                                    static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader, String path) {
                                      try {
                                        Enumeration<URL> urls = (classLoader != null) ? classLoader.getResources(path)
                                          : ClassLoader.getSystemResources(path);
                                        Properties properties = new Properties();
                                        while (urls.hasMoreElements()) {
                                          properties.putAll(PropertiesLoaderUtils.loadProperties(new UrlResource(urls.nextElement())));
                                        }
                                        return loadMetadata(properties);
                                      }
                                      catch (IOException ex) {
                                        throw new IllegalArgumentException("Unable to load @ConditionalOnClass location [" + path + "]", ex);
                                      }
                                    }
                                    ```

                                    

                        2. 进行过滤

                           1. ```java
                              // configurations为127个候选
                              List<String> filter(List<String> configurations) {
                                 long startTime = System.nanoTime();
                                 String[] candidates = StringUtils.toStringArray(configurations);
                                 boolean skipped = false;
                                 // filters=OnBeanCondition,OnClassCondition,OnWebApplicationCondition3个过滤器
                                 for (AutoConfigurationImportFilter filter : this.filters) {
                                    // 在前一个filter过滤的基础上，再次进行过滤
                                    //PropertiesAutoConfigurationMetadata autoConfigurationMetadata即764项 Spring-自动配置-元数据
                                    boolean[] match = filter.match(candidates, this.autoConfigurationMetadata);
                                    for (int i = 0; i < match.length; i++) {
                                       if (!match[i]) { //如果match[i]为false
                                          candidates[i] = null; // candidates[i]被设置为null
                                          skipped = true;
                                       }
                                    }
                                 }
                                 if (!skipped) {
                                    return configurations;
                                 }
                                 List<String> result = new ArrayList<>(candidates.length);
                                 for (String candidate : candidates) {
                                    if (candidate != null) {
                                       result.add(candidate); // 剩下不为空的被添加到result保留下来
                                    }
                                 }
                                 if (logger.isTraceEnabled()) {
                                    int numberFiltered = configurations.size() - result.size();
                                    logger.trace("Filtered " + numberFiltered + " auto configuration class in "
                                          + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms");
                                 }
                                 return result;
                              }
                              ```

                              1. ```java
                                 abstract class FilteringSpringBootCondition extends SpringBootCondition
                                 		implements AutoConfigurationImportFilter, BeanFactoryAware, BeanClassLoaderAware {
                                 	// autoConfigurationClasses 127项EnableAutoConfiguration，
                                   //PropertiesAutoConfigurationMetadata autoConfigurationMetadata 764项AutoConfigurationMetadata
                                   @Override
                                   public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
                                      ConditionEvaluationReport report = ConditionEvaluationReport.find(this.beanFactory);
                                      ConditionOutcome[] outcomes = getOutcomes(autoConfigurationClasses, autoConfigurationMetadata); // 具体过滤匹配
                                      boolean[] match = new boolean[outcomes.length];
                                      for (int i = 0; i < outcomes.length; i++) {
                                         // 结果为空(1-找到class的，2-是servlet也存在GenericWebApplicationContext的,或者是reactive 也存在 HandlerResult的，或者没有在764项中找到的，3-都是命中的，都是带有@ConditionalOnBean注解的)，
                                         // 或者结果匹配（）
                                         // match[i]才会为true
                                         match[i] = (outcomes[i] == null || outcomes[i].isMatch()); 
                                         if (!match[i] && outcomes[i] != null) {
                                            logOutcome(autoConfigurationClasses[i], outcomes[i]);
                                            if (report != null) {
                                               report.recordConditionEvaluation(autoConfigurationClasses[i], this, outcomes[i]);
                                            }
                                         }
                                      }
                                      return match;
                                   }
                                   // ...
                                 }
                                 ```

                                 1. 具体过滤匹配在3个子类中实现

                                    1. OnClassCondition

                                       1. ```java
                                          @Override
                                          protected final ConditionOutcome[] getOutcomes(String[] autoConfigurationClasses,
                                                AutoConfigurationMetadata autoConfigurationMetadata) {
                                             // Split the work and perform half in a background thread if more than one
                                             // processor is available. Using a single additional thread seems to offer the
                                             // best performance. More threads make things worse.
                                             if (autoConfigurationClasses.length > 1 && Runtime.getRuntime().availableProcessors() > 1) { //如果autoConfigurationClasses 长度大于1 并且
                                                return resolveOutcomesThreaded(autoConfigurationClasses, autoConfigurationMetadata);
                                             }
                                             else {
                                                OutcomesResolver outcomesResolver = new StandardOutcomesResolver(autoConfigurationClasses, 0,
                                                      autoConfigurationClasses.length, autoConfigurationMetadata, getBeanClassLoader());
                                                return outcomesResolver.resolveOutcomes();
                                             }
                                          }
                                          ```

                                          1. resolveOutcomesThreaded

                                             ```java
                                             private ConditionOutcome[] resolveOutcomesThreaded(String[] autoConfigurationClasses,
                                                   AutoConfigurationMetadata autoConfigurationMetadata) {
                                                int split = autoConfigurationClasses.length / 2; //127/2 = 63
                                                OutcomesResolver firstHalfResolver = createOutcomesResolver(autoConfigurationClasses, 0, split,
                                                      autoConfigurationMetadata); //得到前一半
                                                OutcomesResolver secondHalfResolver = new StandardOutcomesResolver(autoConfigurationClasses, split,
                                                      autoConfigurationClasses.length, autoConfigurationMetadata, getBeanClassLoader()); //得到后一半
                                                // 分别进行匹配判断得到结果集
                                                ConditionOutcome[] secondHalf = secondHalfResolver.resolveOutcomes();
                                                ConditionOutcome[] firstHalf = firstHalfResolver.resolveOutcomes();
                                                ConditionOutcome[] outcomes = new ConditionOutcome[autoConfigurationClasses.length];
                                                System.arraycopy(firstHalf, 0, outcomes, 0, firstHalf.length);
                                                System.arraycopy(secondHalf, 0, outcomes, split, secondHalf.length);
                                                return outcomes;
                                             }
                                             ```

                                             1. ```java
                                                @Override
                                                public ConditionOutcome[] resolveOutcomes() {
                                                  return getOutcomes(this.autoConfigurationClasses, this.start, this.end, this.autoConfigurationMetadata);
                                                }
                                                
                                                // autoConfigurationClasses127项
                                                // PropertiesAutoConfigurationMetadata autoConfigurationMetadata 764项
                                                private ConditionOutcome[] getOutcomes(String[] autoConfigurationClasses, int start, int end,
                                                                                       AutoConfigurationMetadata autoConfigurationMetadata) {
                                                  // 定义一个结果数组
                                                  ConditionOutcome[] outcomes = new ConditionOutcome[end - start];
                                                  for (int i = start; i < end; i++) {
                                                    String autoConfigurationClass = autoConfigurationClasses[i];
                                                    if (autoConfigurationClass != null) {
                                                      String candidates = autoConfigurationMetadata.get(autoConfigurationClass, "ConditionalOnClass"); // get
                                                      if (candidates != null) {
                                                        outcomes[i - start] = getOutcome(candidates); //getOutcome
                                                      }
                                                    }
                                                  }
                                                  return outcomes;
                                                }
                                                ```

                                                1. get

                                                   1. ```java
                                                      // 将127项目中逐个className，拿到764中去比对，比对的key = [className].ConditionalOnClass,有结果就返回spring自动配置源数据，配置中的值，有可能是数组，没有就返回defaultValue=null
                                                      @Override
                                                      public String get(String className, String key, String defaultValue) {
                                                        String value = this.properties.getProperty(className + "." + key);
                                                        return (value != null) ? value : defaultValue;
                                                      }
                                                      ```

                                                2. getOutcome

                                                   1. ```java
                                                      private ConditionOutcome getOutcome(String className, ClassLoader classLoader) {
                                                        // 拿到ClassNameFilter去匹配，做Class.forName(className, false, classLoader);，如果找到，则返null，如果没有找到，则把不能找到该类记在ConditionOutcome中，并返回
                                                        if (ClassNameFilter.MISSING.matches(className, classLoader)) {
                                                          return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnClass.class)
                                                                                          .didNotFind("required class").items(Style.QUOTE, className));
                                                        }
                                                        return null;
                                                      }
                                                      // 例如： Class<?> aClass = Class.forName("javax.servlet.ServletRegistration"); //能找到
                                                      //Class<?> aClass = Class.forName("javax.jms.ConnectionFactory"); // 抛出异常
                                                      // 以此来判断项目中引入了那些class
                                                      ```

                                          OnClassCondition过滤最终得到，在将spring.factories中127项目EnableAutoConfiguration中逐个className，拿到764中去比对，比对的key = [className].ConditionalOnClass

                                          ```properties
                                          # OnClassCondition过滤最终得到，在将spring.factories中127项目EnableAutoConfiguration中逐个className，拿到764中去比对，比对的key = [className].ConditionalOnClass
                                          # 若对比不到
                                          #		保留
                                          # 对比得到
                                          #		检查源数据所对应的类是否存在
                                          #		若存在
                                          #			保留
                                          # 	不存在
                                          #			清除
                                          # 最终的到
                                          0="org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration"
                                          1="org.springframework.boot.autoconfigure.aop.AopAutoConfiguration"
                                          4="org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration"
                                          6="org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration"
                                          7="org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration"
                                          8="org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration"
                                          9="org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration"
                                          51="org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration"
                                          54="org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration"
                                          56="org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration"
                                          63="org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration"
                                          71="org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration"
                                          76="org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration"
                                          102="org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration" 
                                          103="org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration"  
                                          108="org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration" 
                                          109="org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration" 
                                          111="org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration" 
                                          116="org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration" 
                                          117="org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration" 
                                          118="org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration" 
                                          119="org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration" 
                                          120="org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration" 
                                          121="org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration" 
                                          122="org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration"
                                          123="org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration" 
                                          ```

                                          

                                    2. OnWebApplicationCondition

                                       1. ```java
                                          @Override
                                          protected ConditionOutcome[] getOutcomes(String[] autoConfigurationClasses,
                                                                                   AutoConfigurationMetadata autoConfigurationMetadata) {
                                            ConditionOutcome[] outcomes = new ConditionOutcome[autoConfigurationClasses.length];
                                            for (int i = 0; i < outcomes.length; i++) {
                                              String autoConfigurationClass = autoConfigurationClasses[i];
                                              if (autoConfigurationClass != null) {
                                                outcomes[i] = getOutcome(
                                                  autoConfigurationMetadata.get(autoConfigurationClass, "ConditionalOnWebApplication")); // get 与getOutcome
                                              }
                                            }
                                            return outcomes;
                                          }
                                          ```

                                          1. get 与getOutcome，get与OnClassCondition的类似

                                             ```java
                                             // type 可能为 ""\servlet\reactive
                                             private ConditionOutcome getOutcome(String type) {
                                               // 如果没有在PropertiesAutoConfigurationMetadata autoConfigurationMetadata 764项中找到，也返回null
                                               if (type == null) {
                                                 return null;
                                               }
                                               ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnWebApplication.class);
                                               // 如果是servlet类型
                                               if (ConditionalOnWebApplication.Type.SERVLET.name().equals(type)) {
                                                 // 如果在类路径中不存在org.springframework.web.context.support.GenericWebApplicationContext（通用web应用上下文）
                                                 if (!ClassNameFilter.isPresent(SERVLET_WEB_APPLICATION_CLASS, getBeanClassLoader())) {
                                                   // 在ConditionOutcome中记录不匹配信息，并返回
                                                   return ConditionOutcome.noMatch(message.didNotFind("servlet web application classes").atAll());
                                                 }
                                               }
                                               // 如果是reactive类型
                                               if (ConditionalOnWebApplication.Type.REACTIVE.name().equals(type)) {
                                                 // 如果在类路径中不存在 org.springframework.web.reactive.HandlerResult
                                                 if (!ClassNameFilter.isPresent(REACTIVE_WEB_APPLICATION_CLASS, getBeanClassLoader())) {
                                                   // 在ConditionOutcome中记录不匹配信息
                                                   return ConditionOutcome.noMatch(message.didNotFind("reactive web application classes").atAll());
                                                 }
                                               }
                                               // 如果在类路径中没有org.springframework.web.context.support.GenericWebApplicationContext（通用web应用上下文）并且 如果在类路径中没有 org.springframework.web.reactive.HandlerResult
                                               if (!ClassNameFilter.isPresent(SERVLET_WEB_APPLICATION_CLASS, getBeanClassLoader())
                                                   && !ClassUtils.isPresent(REACTIVE_WEB_APPLICATION_CLASS, getBeanClassLoader())) {
                                                 return ConditionOutcome.noMatch(message.didNotFind("reactive or servlet web application classes").atAll());
                                               }
                                               // 是servlet 也存在 GenericWebApplicationContext
                                               // 是reactive 也存在 HandlerResult
                                               // 不是servlet也不是reactive GenericWebApplicationContext和HandlerResult有其中一个存在
                                               return null;
                                             }
                                             ```

                                          OnWebApplicationCondition在OnClassCondition的基础上，将剩下的拿到拿到764中去比对，比对的key = [className].ConditionalOnWebApplication

                                          ```properties
                                          # OnWebApplicationCondition在OnClassCondition的基础上，将剩下的拿到拿到764中去比对，比对的key = [className].ConditionalOnWebApplication
                                          # 匹配不到
                                          #		保留
                                          # 匹配得到
                                          # 	是servlet 也存在 GenericWebApplicationContext 保留
                                          #		是reactive 也存在 HandlerResult 保留
                                          # 	不是servlet也不是reactive GenericWebApplicationContext和HandlerResult有其中一个存在，保留
                                          #		其他情况，除移
                                          # 最终得到
                                          0="org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration"
                                          1="org.springframework.boot.autoconfigure.aop.AopAutoConfiguration"
                                          4="org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration"
                                          6="org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration"
                                          7="org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration"
                                          8="org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration"
                                          9="org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration"
                                          51="org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration"
                                          54="org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration"
                                          56="org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration"
                                          63="org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration"
                                          71="org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration"
                                          76="org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration"
                                          102="org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration" 
                                          103="org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration"  
                                          108="org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration" 
                                          109="org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration" 
                                          116="org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration" 
                                          117="org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration" 
                                          118="org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration" 
                                          119="org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration" 
                                          120="org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration" 
                                          121="org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration" 
                                          123="org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration" 
                                          ```

                                          

                                    3. OnBeanCondition

                                       1. ```java
                                          @Override
                                          protected final ConditionOutcome[] getOutcomes(String[] autoConfigurationClasses,
                                          AutoConfigurationMetadata autoConfigurationMetadata) {
                                            ConditionOutcome[] outcomes = new ConditionOutcome[autoConfigurationClasses.length];
                                            for (int i = 0; i < outcomes.length; i++) {
                                              String autoConfigurationClass = autoConfigurationClasses[i];
                                              if (autoConfigurationClass != null) {
                                                Set<String> onBeanTypes = autoConfigurationMetadata.getSet(autoConfigurationClass, "ConditionalOnBean");
                                                outcomes[i] = getOutcome(onBeanTypes, ConditionalOnBean.class); // 
                                                if (outcomes[i] == null) { // 如果满足
                                                  Set<String> onSingleCandidateTypes = autoConfigurationMetadata.getSet(autoConfigurationClass,"ConditionalOnSingleCandidate"); //查找autoConfigurationClass.ConditionalOnSingleCandidate
                                                  outcomes[i] = getOutcome(onSingleCandidateTypes,ConditionalOnSingleCandidate.class); // 判断是否满足
                                                }
                                              }
                                            }
                                            // 剩下的都是，要么在spring-autoconfigure-metadata.properties中查找[className].ConditionalOnBean或[className].ConditionalOnSingleCandidate 找不到的，如果找到，values中的类就必须存在，才能留下来
                                            return outcomes;
                                          }
                                          ```

                                          1. getSet 和前两个的类似，不同点是直接将结果装入Set<String>集合

                                          2. getOutcome

                                             ```java
                                             // requiredBeanTypes = 找到的764项其中一项的值
                                             // annotation = @ConditionalOnBean.class
                                             private ConditionOutcome getOutcome(Set<String> requiredBeanTypes, Class<? extends Annotation> annotation) {
                                               List<String> missing = filter(requiredBeanTypes, ClassNameFilter.MISSING, getBeanClassLoader()); // filter，得到未命中的集合，即没有这个类
                                               if (!missing.isEmpty()) { //若未命中的集合不为空，即有未命中的
                                                 ConditionMessage message = ConditionMessage.forCondition(annotation)
                                                   .didNotFind("required type", "required types").items(Style.QUOTE, missing);
                                                 return ConditionOutcome.noMatch(message); //返回未匹配信息
                                               }
                                               return null; // 都命中，返回null
                                             }
                                             ```

                                             1. filter

                                                1. ```java
                                                   // classNames = 找到的764项其中一项的值
                                                   // classNameFilter = ClassNameFilter.MISSING
                                                   // classLoader = beanClassLoader
                                                   protected final List<String> filter(Collection<String> classNames, ClassNameFilter classNameFilter, ClassLoader classLoader) {
                                                     if (CollectionUtils.isEmpty(classNames)) {
                                                       return Collections.emptyList();
                                                     }
                                                     List<String> matches = new ArrayList<>(classNames.size());
                                                     for (String candidate : classNames) {
                                                       if (classNameFilter.matches(candidate, classLoader)) { // 如果没有找到candidate对应的类
                                                         matches.add(candidate); // 则添加到matches当中
                                                       }
                                                     }
                                                     return matches;
                                                   }
                                                   ```

                                          OnBeanCondition在OnWebApplicationCondition的基础上，将剩下的拿到拿到764中去比对，比对的key = [className].ConditionalOnBean或[className].ConditionalOnSingleCandidate

                                          ```properties
                                          # OnBeanCondition在OnWebApplicationCondition的基础上，将剩下的拿到拿到764中去比对，比对的key = [className].ConditionalOnBean或[className].ConditionalOnSingleCandidate
                                          # 如果找不到
                                          # 	保留
                                          # 找得到
                                          #		检查源数据所对应的类是否存在
                                          #		若存在
                                          #			保留
                                          # 	不存在
                                          #			清除
                                          # 最终的到
                                          0="org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration"
                                          1="org.springframework.boot.autoconfigure.aop.AopAutoConfiguration"
                                          2="org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration"
                                          3="org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration"
                                          4="org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration"
                                          5="org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration"
                                          6="org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration"
                                          7="org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration"
                                          8="org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration"
                                          9="org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration"
                                          10="org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration"
                                          11="org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration"
                                          12="org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration"
                                          13="org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration"
                                          14="org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration"
                                          15="org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration"
                                          16="org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration"
                                          17="org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration"
                                          18="org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration"
                                          19="org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration"
                                          20="org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration"
                                          21="org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration"
                                          22="org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration"
                                          ```

                     3. fireAutoConfigurationImportEvents

                     4. 返回，将23项都自动加载到spring中

         2. AutoConfigurationImportSelector实现BeanClassLoaderAware、ResourceLoaderAware、BeanFactoryAware、EnvironmentAware

            

            ​	

            

            

3. @ComponentScan

