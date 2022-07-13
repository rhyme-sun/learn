package learn.spring.dependency.injection.qualifier;

import java.util.Collection;

import learn.spring.dependency.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * QualifierDependencyInjectionExample.
 */
public class QualifierDependencyInjectionExample {

    @Autowired
    @Qualifier("user1")
    private User user1;

    @Autowired
    private Collection<User> allUsers;

    @Autowired
    @Qualifier
    private Collection<User> qualifierUsers;

    @Autowired
    @UserGroup
    private Collection<User> groupedUsers;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierDependencyInjectionExample.class);
        applicationContext.refresh();

        final QualifierDependencyInjectionExample example = applicationContext.getBean(QualifierDependencyInjectionExample.class);
        // user1
        System.out.println(example.user1);
        // user1 user2 user3 user4 user5
        System.out.println(example.allUsers);
        // user2 user3 user4 user5
        System.out.println(example.qualifierUsers);
        // user4 user5
        System.out.println(example.groupedUsers);
        applicationContext.close();
    }

    @Bean
    public User user1() {
        return createUser(1);
    }

    @Bean
    @Qualifier
    public User user2() {
        return createUser(2);
    }

    @Bean
    @Qualifier
    public User user3() {
        return createUser(3);
    }

    @Bean
    @UserGroup
    public User user4() {
        return createUser(4);
    }

    @Bean
    @UserGroup
    public User user5() {
        return createUser(5);
    }

    private User createUser(Integer id) {
        final User user = new User();
        user.setId(id);
        return user;
    }
}
