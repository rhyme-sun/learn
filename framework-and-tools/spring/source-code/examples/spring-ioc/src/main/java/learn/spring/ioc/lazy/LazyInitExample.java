package learn.spring.ioc.lazy;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * LazyInitExample.
 */
public class LazyInitExample {

    public static void main(String[] args) {
        // FactoryBean
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:lazy-init.xml");
        // scope="prototype"
        System.out.println(beanFactory.getBean("&fooFactoryBean")); // FactoryBean instance
        //System.out.println(beanFactory.getBean("&fooFactoryBean")); // FactoryBean instance
        System.out.println(beanFactory.getBean("fooFactoryBean"));  // Foo instance
        System.out.println(beanFactory.getBean("fooFactoryBean"));  // Foo instance same as above

        // ObjectFactory
        System.out.println(beanFactory.getBean("fooObjectFactory")); // ObjectFactory instance
        System.out.println(((ObjectFactory) beanFactory.getBean("fooObjectFactory")).getObject()); // Foo instance
        System.out.println(((ObjectFactory) beanFactory.getBean("fooObjectFactory")).getObject()); // Foo instance different as above.

        // ObjectProvider
        ObjectProvider<String> stringObjectProvider = beanFactory.getBeanProvider(String.class);
        // Safe method
        System.out.println(stringObjectProvider.getIfAvailable());
        System.out.println(stringObjectProvider.getObject());
    }
}
