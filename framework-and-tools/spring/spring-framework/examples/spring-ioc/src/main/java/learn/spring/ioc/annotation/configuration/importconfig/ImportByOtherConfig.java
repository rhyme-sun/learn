package learn.spring.ioc.annotation.configuration.importconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportByOtherConfig {

    @Bean
    public ImportedBean importByOtherConfig() {
        return new ImportedBean("importByOtherConfig");
    }
}
