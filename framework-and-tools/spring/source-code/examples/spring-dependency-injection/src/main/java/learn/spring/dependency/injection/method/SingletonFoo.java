package learn.spring.dependency.injection.method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * SingletonFoo.
 */
public class SingletonFoo implements BeanFactoryAware {

    private BeanFactory beanFactory;

    private PrototypeFoo prototypeFoo;

    public PrototypeFoo getPrototypeFoo() {
        return prototypeFoo;
    }

    public PrototypeFoo getPrototypeFooByAware() {
        return beanFactory.getBean(PrototypeFoo.class);
    }
    
    public void method() {
        System.out.println("method...");
    }

    public void setPrototypeFoo(PrototypeFoo prototypeFoo) {
        this.prototypeFoo = prototypeFoo;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
