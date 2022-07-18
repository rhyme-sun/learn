package learn.spring.ioc.annotation.configuration.mode;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Configuration
@Component
public class AppConfig {

    @Bean
    public FooService fooService() {
        return new FooService(fooRepository());
    }

    @Bean
    public FooRepository fooRepository() {
        return new FooRepository();
    }
}