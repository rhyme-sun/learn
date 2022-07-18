# Spring Boot



## Spring Boot 好处？

简化配置，提高生产力。



## Spring Boot Starters?

[你一直用的 Spring Boot Starter 是怎么回事 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/67484886)

起步依赖简化了依赖项的配置，比如我们只需要添加某个组件的起步依赖，就可以使用该组件了，而不用关系该组件其它的依赖项。



自定义 stater

一个起步依赖包含四个要素：

- starter 命名 ;

  spring-boot-starter-{name} 

- 自动配置类，用来初始化相关的 bean ;

- 指明自动配置类的配置文件 spring.factories ;

- 自定义属性实体类，声明 starter 的应用配置属性 ;



spring-boot-starter

spring-boot-starter 中就是官方提供的主要 starter，比如 jdbc、redis、security、web 等等。





## Spring Boot 自动配置原理

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {

    /**
	 * Environment property that can be used to override when auto-configuration is
	 * enabled.
	 */
    String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

    /**
	 * Exclude specific auto-configuration classes such that they will never be applied.
	 * @return the classes to exclude
	 */
    Class<?>[] exclude() default {};

    /**
	 * Exclude specific auto-configuration class names such that they will never be
	 * applied.
	 * @return the class names to exclude
	 * @since 1.3.0
	 */
    String[] excludeName() default {};

}
```



`ConfigurationClassPostProcessor`

`AutoConfigurationImportSelector`

```java
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    List<String> configurations = new ArrayList<>(
        SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader()));
    ImportCandidates.load(AutoConfiguration.class, getBeanClassLoader()).forEach(configurations::add);
    Assert.notEmpty(configurations,
                    "No auto configuration classes found in META-INF/spring.factories nor in META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports. If you "
                    + "are using a custom packaging, make sure that file is correct.");
    return configurations;
}
```



Spring Boot 启动类声明了 `@SpringBootApplication`，组合了 `@Configuration` 和 `@EnableAutoConfiguration`，`@EnableAutoConfiguration` 组合了`@Import`，其导入了 `AutoConfigurationImportSelector`。



这样在上下文容器启动（刷新）的过程中，会执行 `ConfigurationClassPostProcessor` ，这个 `BFPP` 用来解析配置类，注册一些 bean 定义信息。

解析配置类的过程中会调用 `AutoConfigurationImportSelector#getCandidateConfigurations` 方法，执行自动配置类导入逻辑。



导入逻辑总体上为读取 classpath 下 META-INF/spring.factories 文件，获取 `AutoConfiguration`  对应的自动配置类的全路径。

过滤调一些不需要的配置，然后回到 `ConfigurationClassPostProcessor` 对这些配置了进行注册，注册的时候还需根据条件注解，注册满足条件的 bean。



## 配置文件加载优先级

[Core Features (spring.io)](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#boot-features-external-config)

优先级最高：项目根目录下 config 下的 application.properties；

优先级第二高，resources/config 文件下的 application.properties；

优先级最低：resources 下的 application.properties。



优先级低的配置会被优先级高的相同配置给覆盖。

