package learn.spring.ioc.refresh.xml;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * MyBeanDefinitionRegistryPostProcessor.
 * MyBeanDefinitionRegistryPostProcessor 后置处理逻辑中注册了新的 BeanDefinitionRegistryPostProcessor.
 */
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry...");
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(InnerBeanDefinitionRegistryPostProcessor.class);
        registry.registerBeanDefinition("innerBeanDefinitionRegistryPostProcessor", builder.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor#postProcessBeanFactory...");
    }

    static class InnerBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            System.out.println("InnerBeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry...");
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            System.out.println("InnerBeanDefinitionRegistryPostProcessor#postProcessBeanFactory...");
        }
    }
}
