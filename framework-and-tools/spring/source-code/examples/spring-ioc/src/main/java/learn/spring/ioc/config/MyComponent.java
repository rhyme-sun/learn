package learn.spring.ioc.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyComponent.
 */
//@Component
//@ImportResource("classpath:inner-bean.xml")
@Configuration
public class MyComponent {

    @Bean
    public String myComponentString() {
        return "myComponentString";
    }

    // @Bean method MyComponent.componentBeanFactoryPostProcessor is non-static and returns an object assignable to
    // Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as
    // @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class.
    // Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
    @Bean
    public static ComponentBeanFactoryPostProcessor componentBeanFactoryPostProcessor() {
        return new ComponentBeanFactoryPostProcessor();
    }

    static class ComponentBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            System.out.println("@Bean-BeanFactoryPostProcessor...");
        }
    }
}
