package learn.spring.ioc.annotation.configuration.lookup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * AppConfig.
 */
@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public AsyncCommand asyncCommand() {
        AsyncCommand command = new AsyncCommand();
        // inject dependencies here as required
        return command;
    }

    @Bean
    public CommandManager commandManager() {
        // return new anonymous implementation of CommandManager with createCommand()
        // overridden to return a new prototype Command object
        return new CommandManager() {
            protected Command createCommand() {
                return asyncCommand();
            }
        };
    }
}
