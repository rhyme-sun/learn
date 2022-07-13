package learn.spring.dependency.injection.field;

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
 * AnnotationDependencyFieldInjectionExample.
 */
public class AnnotationDependencyFieldInjectionExample {

    @Autowired
    private UserHolder userHolder;
    // Autowired 注解会忽略掉静态字段
    @Autowired
    private static UserHolder staticUserHolder;

    @Resource
    private UserHolder resourceUserHolder;

    @Inject
    private UserHolder injectUserHolder;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationDependencyFieldInjectionExample.class);

        loadXmlBeanDefinition(applicationContext);

        applicationContext.refresh();

        final UserHolder bean = applicationContext.getBean(UserHolder.class);
        System.out.println(bean);

        final AnnotationDependencyFieldInjectionExample example = applicationContext.getBean(AnnotationDependencyFieldInjectionExample.class);
        System.out.println(example.userHolder);
        // true
        System.out.println(bean == example.userHolder);

        // null
        System.out.println(staticUserHolder);
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

    @Bean
    public UserHolder userHolder(User user) {
        final UserHolder userHolder = new UserHolder();
        userHolder.setUser(user);
        return userHolder;
    }
}
