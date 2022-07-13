package learn.spring.ioc.annotation.configuration.profile;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

    @Configuration
    public class ProfileDatabaseConfig {

        @Bean("dataSource")
        @Profile("development")
        public DataSource embeddedDatabase() {
            return new UnusableDataSource("development");
        }

        @Bean("dataSource")
        @Profile("production")
        public DataSource productionDatabase() {
            return new UnusableDataSource("production");
        }
    }