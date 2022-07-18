package learn.spring.ioc.annotation.configuration.importconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ImportByOtherConfig.class, ImportByImportSelector.class, ImportByImportBeanDefinitionRegistrar.class})
public class AppConfig {

}
