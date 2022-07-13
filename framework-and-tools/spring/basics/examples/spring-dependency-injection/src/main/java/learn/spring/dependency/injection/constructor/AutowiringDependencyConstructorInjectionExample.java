package learn.spring.dependency.injection.constructor;

import java.util.Map;

import learn.spring.dependency.domain.UserHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 根据 Bean 的名称自动注入
 *
 * AutowiringDependencyConstructorInjectionExample.
 */
public class AutowiringDependencyConstructorInjectionExample {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String path = "classpath:/META-INF/autowiring-dependency-constructor-injection-context.xml";
        reader.loadBeanDefinitions(path);

        final Map<String, UserHolder> beansOfType = beanFactory.getBeansOfType(UserHolder.class);
        System.out.println(beansOfType);
    }
}
