package learn.spring.bean.definition;

import java.util.Map;

import learn.spring.bean.domain.User;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * AnnotationBeanDefinitionExample.
 * 使用注解注册 Bean
 */
// 通过 @Import 注解导入配置元信息
@Import(AnnotationBeanDefinitionExample.Config.class)
public class AnnotationBeanDefinitionExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册配置类
        applicationContext.register(AnnotationBeanDefinitionExample.class);
        applicationContext.refresh();

        registerUserBeanDefinition(applicationContext, "apiUser");
        registerUserBeanDefinition(applicationContext);

        final Map<String, Config> configBeans = applicationContext.getBeansOfType(Config.class);
        final Map<String, User> userBeans = applicationContext.getBeansOfType(User.class);
        System.out.println(configBeans);
        System.out.println(userBeans);

        registerOuterObject(applicationContext);
        applicationContext.close();
    }

    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("id", 1)
                .addPropertyValue("name", "Simon")
                .addPropertyValue("age", 18);
        final AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        if (StringUtils.hasText(beanName)) {
            // 命名方式注册 Bean
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
        }
    }

    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry) {
        registerUserBeanDefinition(registry, null);
    }

    public static void registerOuterObject(AnnotationConfigApplicationContext applicationContext) {
        final ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        User user = new User();
        user.setName("OuterUser-Simon");
        beanFactory.registerSingleton("outerUser", user);
        final Object outerUser = applicationContext.getBean("outerUser");
        System.out.println(outerUser == user);
    }

    // 通过 @Component 注解将当前对象注册为 Bean
    @Component
    // @Configuration
    public static class Config {

        // 通过 @Bean 注解
        @Bean(name = {"user", "aliasUser"})
        public User user() {
            User user = new User();
            user.setAge(1);
            user.setAge(18);
            user.setName("Simon");
            return user;
        }
    }
}
