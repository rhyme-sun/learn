package learn.spring.dependency.lookup;

import java.util.concurrent.TimeUnit;

import learn.spring.dependency.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * BeanProviderExample.
 */
public class BeanProviderExample {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanProviderExample.class);
        applicationContext.refresh();

        final ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);
        System.out.println("GetObject: " + beanProvider.getObject());
        System.out.println("GetIfAvailable: " + beanProvider.getIfAvailable());

        beanProvider.forEach(System.out::println);


        final ObjectProvider<User> userBeanProvider = applicationContext.getBeanProvider(User.class);

        // ObjectProvider.getIfAvailable(java.util.function.Supplier<T>) 不存在每次都会重新创建
        System.out.println(userBeanProvider.getIfAvailable(User::createUser));
        TimeUnit.SECONDS.sleep(5);
        System.out.println(userBeanProvider.getIfAvailable(User::createUser));

        applicationContext.close();
    }

    @Bean
    @Primary
    public String hello() {
        return "Hello";
    }

    @Bean
    public String world() {
        return "World";
    }
}
