package learn.spring.dependency.injection;

import java.util.Map;
import java.util.Optional;

import learn.spring.dependency.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

/**
 * DependencyInjectionResolutionExample.
 *
 * 依赖处理解析，入口 {@link DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String)}
 */
public class DependencyInjectionResolutionExample {

    @Autowired
    private User user;

    @Autowired
    private Map<String, User> userMap;

    @Autowired
    private Optional<User> optionalUser;

    @Autowired
    @Lazy
    private User lazyUser;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(DependencyInjectionResolutionExample.class);
        applicationContext.refresh();

        final DependencyInjectionResolutionExample example = applicationContext.getBean(DependencyInjectionResolutionExample.class);
        System.out.println(example.user);
        System.out.println(example.userMap);
        System.out.println(example.optionalUser);
        System.out.println(example.lazyUser);

        applicationContext.close();
    }

    @Bean
    @Primary
    public User user() {
        return new User(1, "Simon", 28);
    }

    @Bean
    public User user2() {
        return new User(2, "Simon", 28);
    }
}
