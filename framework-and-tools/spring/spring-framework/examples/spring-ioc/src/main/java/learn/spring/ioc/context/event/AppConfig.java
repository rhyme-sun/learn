package learn.spring.ioc.context.event;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * AppConfig.
 */
@Configuration
@ComponentScan(basePackages = {"learn.spring.ioc.context.event"})
@PropertySource("classpath:event.properties")
public class AppConfig {

}
