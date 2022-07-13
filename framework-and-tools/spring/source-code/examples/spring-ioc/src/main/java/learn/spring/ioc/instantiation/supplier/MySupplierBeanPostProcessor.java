package learn.spring.ioc.instantiation.supplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * MySupplierBeanPostProcessor.
 */
public class MySupplierBeanPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition supplierBean = (GenericBeanDefinition) beanFactory.getBeanDefinition("supplierBean");
        supplierBean.setInstanceSupplier(new MyBeanSupplier());
    }
}
