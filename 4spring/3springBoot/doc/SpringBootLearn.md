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

