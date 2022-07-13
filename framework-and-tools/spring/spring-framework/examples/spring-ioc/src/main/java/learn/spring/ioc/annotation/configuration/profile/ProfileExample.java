package learn.spring.ioc.annotation.configuration.profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ProfileExample.
 */
public class ProfileExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(ProfileDatabaseConfig.class);
        ac.getEnvironment().setActiveProfiles("production");
        ac.refresh();
        // 激活 profile 还可以
        //System.setProperty("spring.profiles.active", "production");
        // -Dspring.profiles.active="production"

        System.out.println(ac.getEnvironment().getProperty("spring.profiles.active"));
        System.out.println(ac.getBean(UnusableDataSource.class));
    }
}
