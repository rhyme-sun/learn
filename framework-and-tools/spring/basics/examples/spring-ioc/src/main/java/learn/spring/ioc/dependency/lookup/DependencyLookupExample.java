package learn.spring.ioc.dependency.lookup;

import java.util.Map;

import learn.spring.ioc.dependency.domain.Super;
import learn.spring.ioc.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * DependencyLookupExample.
 */
public class DependencyLookupExample {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");

        lookupByName(beanFactory);
        lookupByType(beanFactory);
        lookupCollectionByType(beanFactory);

        lookupByAnnotation(beanFactory);

        lookupInRealTime(beanFactory);
        lookupInLazy(beanFactory);
    }

    private static void lookupByName(BeanFactory beanFactory) {
        final Object user = beanFactory.getBean("user");
        System.out.println("名称查找：" + user);
    }

    private static void lookupByAnnotation(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            final Map<String, Object> beansWithAnnotation = listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找所有 Super 用户：" + beansWithAnnotation);
        }
    }

    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            final Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找所有 User：" + beansOfType);
        }
    }

    private static void lookupByType(BeanFactory beanFactory) {
        final User user = beanFactory.getBean(User.class);
        System.out.println("类型查找：" + user);
    }

    private static void lookupInRealTime(BeanFactory beanFactory) {
        final User user = beanFactory.getBean("user", User.class);
        System.out.println("实时查找：" + user);
    }

    private static void lookupInLazy(BeanFactory beanFactory) {
        final ObjectFactory<User> factory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        final User user = factory.getObject();
        System.out.println("延迟查找：" + user);
    }
}
