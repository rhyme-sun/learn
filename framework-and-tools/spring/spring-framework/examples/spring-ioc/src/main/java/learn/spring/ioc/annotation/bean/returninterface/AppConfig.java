package learn.spring.ioc.annotation.bean.returninterface;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig.
 */
@Configuration
public class AppConfig {

    @Bean
    public TransferService transferService() {
        return new TransferServiceImpl();
    }

    @Bean
    public TransferServiceImpl transferServiceImpl() {
        return new TransferServiceImpl();
    }
}
