package learn.spring.ioc.annotation.configuration.importresource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * AppConfig.
 */
@Configuration
@ImportResource("classpath:import-resource.xml")
public class AppConfig {

    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DbConfig dataSource() {
        return new DbConfig(url, username, password);
    }
}
