package learn.spring.dependency.injection.method;

import javax.annotation.Resource;
import javax.inject.Inject;

import learn.spring.dependency.domain.User;
import learn.spring.dependency.domain.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * AnnotationDependencyMethodInjectionExample.
 */
public class AnnotationDependencyMethodInjectionExample {

    private UserHolder userHolder;
    private UserHolder resourceUserHolder;
    private UserHolder injectUserHolder;

    @Autowired
    private void initUserHolder(UserHolder userHolder) {
        this.userHolder = userHolder;
    }

    @Resource
    private void initResourceUserHolder(UserHolder userHolder) {
        this.resourceUserHolder = userHolder;
    }

    @Inject
    private void initInjectUserHolder(UserHolder userHolder) {
        this.injectUserHolder = userHolder;
    }

    @Bean
    public UserHolder userHolder(User user) {
        final UserHolder userHolder = new UserHolder();
        userHolder.setUser(user);
        return userHolder;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationDependencyMethodInjectionExample.class);

        loadXmlBeanDefinition(applicationContext);

        applicationContext.refresh();

        final UserHolder bean = applicationContext.getBean(UserHolder.class);
        System.out.println(bean);

        final AnnotationDependencyMethodInjectionExample example = applicationContext.getBean(AnnotationDependencyMethodInjectionExample.class);
        System.out.println(example.userHolder);
        // true
        System.out.println(bean == example.userHolder);

        System.out.println(example.resourceUserHolder);
        System.out.println(example.userHolder == example.resourceUserHolder);

        System.out.println(example.injectUserHolder);
        System.out.println(example.userHolder == example.injectUserHolder);
        applicationContext.close();
    }

    private static void loadXmlBeanDefinition(BeanDefinitionRegistry beanDefinitionRegistry) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
        String path = "classpath:/META-INF/user-context.xml";
        reader.loadBeanDefinitions(path);
    }
}
