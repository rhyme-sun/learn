package learn.spring.ioc.instantiation.callback;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * MyInstantiationBeanPostProcessor.
 */
public class MyInstantiationBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationBeanPostProcessor#postProcessBeforeInitialization...");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationBeanPostProcessor#postProcessAfterInitialization...");
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("MyInstantiationBeanPostProcessor#postProcessBeforeInstantiation...");
        if (BeforeInstantiation.class.isAssignableFrom(beanClass)) {
            // 创建一个代理实例
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(BeforeInstantiation.class);
            enhancer.setCallback(new MyMethodInterceptor());
            return enhancer.create();
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationBeanPostProcessor#postProcessAfterInstantiation...");
        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationBeanPostProcessor#postProcessProperties...");
        return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
    }
}
