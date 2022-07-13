package learn.spring.dependency.injection.setter;

import learn.spring.dependency.domain.User;
import learn.spring.dependency.domain.UserHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * AnnotationDependencySetterInjectionExample.
 */
public class AnnotationDependencySetterInjectionExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationDependencySetterInjectionExample.class);

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
        final UserHolder userHolder = new UserHolder();
        userHolder.setUser(user);
        return userHolder;
    }
}
