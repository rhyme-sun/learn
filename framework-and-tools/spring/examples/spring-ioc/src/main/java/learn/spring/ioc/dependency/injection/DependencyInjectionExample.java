package learn.spring.ioc.dependency.injection;

import learn.spring.ioc.dependency.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * DependencyLookupExample.
 */
public class DependencyInjectionExample {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        // 自定义 Bean
        final UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        System.out.println(userRepository.getUsers());

        // 容器内建依赖
        System.out.println(userRepository.getBeanFactory());
        // 容器内建依赖不是 Bean 对象（下行代码会报错：NoSuchBeanDefinitionException）
        // System.out.println(beanFactory.getBean(BeanFactory.class));

        // 容器内建 Bean
        final Environment environment = beanFactory.getBean(Environment.class);
        System.out.println(environment);

        beanFactoryAndApplicationContext(beanFactory, userRepository);
    }

    private static void beanFactoryAndApplicationContext(BeanFactory beanFactory,  UserRepository userRepository) {
        // ApplicationContext is a sub-interface of BeanFactory.
        // false
        System.out.println(beanFactory == userRepository.getBeanFactory());
        // ApplicationContext 组合了一个 BeanFactory 对象，在 AbstractRefreshableApplicationContext 中可以看出
        // 组合的这个 BeanFactory 对象协助我们来通过上下文来进行一些操作，比如获取
    }
}
