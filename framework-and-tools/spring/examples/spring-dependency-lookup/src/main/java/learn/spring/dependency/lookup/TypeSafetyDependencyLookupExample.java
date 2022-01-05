package learn.spring.dependency.lookup;

import java.util.Map;

import learn.spring.dependency.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 类型安全依赖查找。
 * TypeSafetyDependencyLookupExample.
 */
public class TypeSafetyDependencyLookupExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TypeSafetyDependencyLookupExample.class);
        applicationContext.refresh();

        // BeanFactory#getBean 依赖查找不安全
        displayBeanFactoryGetBean(applicationContext);
        // ObjectFactory#getObject 依赖查找不安全
        displayFactoryBeanGetObject(applicationContext);
        // ObjectProvider#getIfAvailable 依赖查找是安全的
        displayObjectProviderGetIfAvailable(applicationContext);

        // ListableBeanFactory#getBeansOfType 依赖查找是安全的
        displayListableBeanFactoryGetBeansOfType(applicationContext);
        // ObjectProvider#stream 依赖查找是安全的
        displayObjectProviderStream(applicationContext);
        applicationContext.close();
    }

    private static void displayBeanFactoryGetBean(BeanFactory beanFactory) {
        try {
            final User bean = beanFactory.getBean(User.class);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private static void displayFactoryBeanGetObject(BeanFactory beanFactory) {
        try {
            final ObjectProvider<User> beanProvider = beanFactory.getBeanProvider(User.class);
            final User object = beanProvider.getObject();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private static void displayObjectProviderGetIfAvailable(BeanFactory beanFactory) {
        try {
            final ObjectProvider<User> beanProvider = beanFactory.getBeanProvider(User.class);
            final User ifAvailable = beanProvider.getIfAvailable();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private static void displayListableBeanFactoryGetBeansOfType(ListableBeanFactory listableBeanFactory) {
        try {
            final Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private static void displayObjectProviderStream(BeanFactory beanFactory) {
        try {
            final ObjectProvider<User> beanProvider = beanFactory.getBeanProvider(User.class);
            beanProvider.stream().forEach(user -> {
                System.out.println(user);
            });
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}
