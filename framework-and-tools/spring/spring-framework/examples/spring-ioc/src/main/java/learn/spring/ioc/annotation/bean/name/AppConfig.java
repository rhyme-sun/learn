package learn.spring.ioc.annotation.bean.name;

import org.springframework.context.annotation.Bean;

public class AppConfig {

    @Bean({"foo1", "foo2", "foo3"})
    public Foo foo() {
        return new Foo();
    }
}