package learn.spring.dependency.injection;

import learn.spring.dependency.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * QualifierDependencyInjectionExample.
 */
public class LazyDependencyInjectionExample {

    // 实时注入
    @Autowired
    private User user1;

    // 延迟注入
    @Autowired
    private ObjectFactory<User> userObjectFactory;
    @Autowired
    private ObjectProvider<User> userObjectProvider;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(LazyDependencyInjectionExample.class);
        applicationContext.refresh();

        final LazyDependencyInjectionExample example = applicationContext.getBean(LazyDependencyInjectionExample.class);
        // user1
        System.out.println(example.user1);

        System.out.println(example.userObjectFactory.getObject());

        System.out.println();
        example.userObjectProvider.forEach(System.out::println);
        applicationContext.close();
    }

    @Bean
    @Primary
    public User user1() {
        return createUser(1);
    }

    @Bean
    public User user2() {
        return createUser(2);
    }

    private User createUser(Integer id) {
        final User user = new User();
        user.setId(id);
        return user;
    }
}
