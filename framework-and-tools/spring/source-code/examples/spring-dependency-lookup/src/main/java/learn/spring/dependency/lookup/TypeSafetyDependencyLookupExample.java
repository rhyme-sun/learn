package learn.spring.dependency.lookup;

import java.util.Map;

import learn.spring.dependency.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 类型安全依赖查找。
 * TypeSafetyDependencyLookupExample.
 */
public class TypeSafetyDependencyLookupExample {

    @Bean
    public User user1() {
        final User user = new User();
        user.setName("Simon-1");
        return user;
    }

    @Bean
    public User user2() {
        final User user = new User();
        user.setName("Simon-2");
        return user;
    }

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
            System.out.println(bean);
        } catch (BeansException e) {
            System.err.println("displayBeanFactoryGetBean: " + e.getMessage());
        }
    }

    private static void displayFactoryBeanGetObject(BeanFactory beanFactory) {
        try {
            final ObjectProvider<User> beanProvider = beanFactory.getBeanProvider(User.class);
            final User object = beanProvider.getObject();
            System.out.println(object);
        } catch (BeansException e) {
            System.err.println("displayFactoryBeanGetObject: " + e.getMessage());
        }
    }

    private static void displayObjectProviderGetIfAvailable(BeanFactory beanFactory) {
        try {
            final ObjectProvider<User> beanProvider = beanFactory.getBeanProvider(User.class);
            final User ifAvailable = beanProvider.getIfAvailable();
            System.out.println(ifAvailable);
        } catch (BeansException e) {
            System.err.println("displayObjectProviderGetIfAvailable: " + e.getMessage());
        }
    }

    private static void displayListableBeanFactoryGetBeansOfType(ListableBeanFactory listableBeanFactory) {
        try {
            final Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println(beansOfType);
        } catch (BeansException e) {
            System.err.println("displayListableBeanFactoryGetBeansOfType: " + e.getMessage());
        }
    }

    private static void displayObjectProviderStream(BeanFactory beanFactory) {
        try {
            final ObjectProvider<User> beanProvider = beanFactory.getBeanProvider(User.class);
            beanProvider.stream().forEach(System.out::println);
        } catch (BeansException e) {
            System.err.println("displayObjectProviderStream: " + e.getMessage());
        }
    }
}
