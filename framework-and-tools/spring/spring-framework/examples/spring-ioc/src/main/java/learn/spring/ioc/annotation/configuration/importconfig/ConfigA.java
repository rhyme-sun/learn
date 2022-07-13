package learn.spring.ioc.annotation.configuration.importconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigA {

    @Bean
    public A a() {
        return new A();
    }
}
