package learn.spring.bean.definition;

import learn.spring.bean.domain.InitMethodUserFactory;
import learn.spring.bean.domain.UserFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * BeanInitializationExample.
 */
@Configuration
public class BeanInitializationExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationExample.class);
        applicationContext.refresh();

        final UserFactory initMethodUserFactory = applicationContext.getBean("initMethodUserFactory", UserFactory.class);

        // API 方式指定 Bean 的初始化方法
        registerUserBeanDefinition(applicationContext, "apiInitUserFactory");
        applicationContext.getBean("apiInitUserFactory");

        applicationContext.close();
    }

    @Bean(initMethod = "annotationInitMethod")
    public InitMethodUserFactory initMethodUserFactory() {
        return new InitMethodUserFactory();
    }

    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(InitMethodUserFactory.class);
        final AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // 指定初始化方法
        beanDefinition.setInitMethodName("apiInitMethod");
        if (StringUtils.hasText(beanName)) {
            // 命名方式注册 Bean
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
        }
    }
}
