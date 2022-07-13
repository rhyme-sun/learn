package learn.spring.dependency.injection.constructor;

import learn.spring.dependency.domain.User;
import learn.spring.dependency.domain.UserHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * AnnotationDependencyConstructorInjectionExample.
 */
public class AnnotationDependencyConstructorInjectionExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationDependencyConstructorInjectionExample.class);

        loadXmlBeanDefinition(applicationContext);

        applicationContext.refresh();

        final UserHolder bean = applicationContext.getBean(UserHolder.class);
        System.out.println(bean);

        applicationContext.close();
    }

    private static void loadXmlBeanDefinition(BeanDefinitionRegistry beanDefinitionRegistry) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
        String path = "classpath:/META-INF/user-context.xml";
        reader.loadBeanDefinitions(path);
    }

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }
}
