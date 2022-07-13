package learn.spring.ioc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * MyPropertySource
 */
@Configuration
@PropertySource({"classpath:my-config.properties"})
public class MyPropertySource {

    @Value("${foo:foo}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
