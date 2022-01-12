package learn.spring.dependency.injection.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * AwareDependencyInjectionExample.
 */
public class AwareDependencyInjectionExample implements BeanFactoryAware, BeanNameAware {

    private BeanFactory beanFactory;
    private transient String beanName;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AwareDependencyInjectionExample.class);

        applicationContext.refresh();

        final AwareDependencyInjectionExample example = applicationContext.getBean(AwareDependencyInjectionExample.class);

        System.out.println(example.beanFactory);
        System.out.println(example.beanName);

        applicationContext.close();
    }
}
