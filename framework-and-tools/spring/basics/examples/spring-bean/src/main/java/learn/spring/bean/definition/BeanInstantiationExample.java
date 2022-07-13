package learn.spring.bean.definition;

import java.util.Optional;
import java.util.ServiceLoader;

import learn.spring.bean.domain.DefaultUserFactory;
import learn.spring.bean.domain.User;
import learn.spring.bean.domain.UserFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Bean 实例化
 * <p>
 * BeanInstantiationExample.
 */
public class BeanInstantiationExample {

    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        final User constructorUser = beanFactory.getBean("constructorUser", User.class);
        System.out.println("通过构造器实例化 Bean：" + constructorUser);

        final User factoryMethodUser = beanFactory.getBean("factoryMethodUser", User.class);
        System.out.println("通过静态工厂方法实例化 Bean：" + factoryMethodUser);

        final User factoryUser = beanFactory.getBean("factoryUser", User.class);
        System.out.println("通过工厂方法实例化 Bean：" + factoryUser);

        final User userByFactoryBean = beanFactory.getBean("userByFactoryBean", User.class);
        System.out.println("通过 FactoryBean 实例化 Bean：" + userByFactoryBean);

        serviceLoaderExample();
        serviceLoaderFactoryBean(beanFactory);
        autowireCapableBeanFactory();
    }

    /**
     * SPI 获取扩展 Bean
     */
    private static void serviceLoaderExample() {
        ServiceLoader<UserFactory> serviceLoader = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        final Optional<UserFactory> first = serviceLoader.findFirst();
        if (first.isPresent()) {
            System.out.println("通过 ServiceLoader 加载的 UserFactory 实例化 Bean：" + first.get().createUser());
        }
    }

    /**
     * 通过 ServiceLoaderFactoryBean 获取 ServiceLoader 实例，再获取扩展 Bean
     *
     * @param beanFactory BeanFactory
     */
    private static void serviceLoaderFactoryBean(BeanFactory beanFactory) {
        final ServiceLoader<UserFactory> serviceLoaderByFactoryBean = beanFactory.getBean("serviceLoaderByFactoryBean", ServiceLoader.class);
        final Optional<UserFactory> first = serviceLoaderByFactoryBean.findFirst();
        if (first.isPresent()) {
            System.out.println("通过 ServiceLoaderFactoryBean 创建的 ServiceLoader 加载的 UserFactory 实例化 Bean：" + first.get().createUser());
        }
    }

    /**
     * 通过 AutowireCapableBeanFactory 创建 Bean
     */
    private static void autowireCapableBeanFactory() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        final AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();

        final DefaultUserFactory userFactory = autowireCapableBeanFactory.createBean(DefaultUserFactory.class);
        System.out.println("通过 AutowireCapableBeanFactory 实例化 Bean：" + userFactory.createUser());
    }
}
