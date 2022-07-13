package learn.spring.aop.annotation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages="learn.spring.aop.annotation")
@EnableAspectJAutoProxy
public class AppConfig {

}
