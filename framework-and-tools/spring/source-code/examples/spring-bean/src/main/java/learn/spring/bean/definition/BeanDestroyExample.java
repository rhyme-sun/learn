package learn.spring.bean.definition;

import learn.spring.bean.domain.DestroyMethodUserFactory;
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
 * BeanDestroyExample.
 */
@Configuration
public class BeanDestroyExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanDestroyExample.class);
        applicationContext.refresh();

        final UserFactory destroyMethodUserFactory = applicationContext.getBean("destroyMethodUserFactory", UserFactory.class);

        // API 方式指定 Bean 的销毁方法
        registerUserBeanDefinition(applicationContext, "apiDestroyUserFactory");
        applicationContext.getBean("apiDestroyUserFactory");

        applicationContext.close();
    }

    @Bean(destroyMethod = "annotationDestroyMethod")
    public DestroyMethodUserFactory destroyMethodUserFactory() {
        return new DestroyMethodUserFactory();
    }

    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DestroyMethodUserFactory.class);
        final AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // 指定初始化方法
        beanDefinition.setDestroyMethodName("apiDestroyMethod");
        if (StringUtils.hasText(beanName)) {
            // 命名方式注册 Bean
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
        }
    }
}
