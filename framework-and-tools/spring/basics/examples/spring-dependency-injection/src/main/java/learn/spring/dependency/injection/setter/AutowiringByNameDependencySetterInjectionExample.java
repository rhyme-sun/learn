package learn.spring.dependency.injection.setter;

import java.util.Map;

import learn.spring.dependency.domain.UserHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 根据 Bean 的名称自动注入
 *
 * AutowiringByNameDependencySetterInjectionExample.
 */
public class AutowiringByNameDependencySetterInjectionExample {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String path = "classpath:/META-INF/autowiring-dependency-setter-injection-context.xml";
        reader.loadBeanDefinitions(path);

        final Map<String, UserHolder> beansOfType = beanFactory.getBeansOfType(UserHolder.class);
        System.out.println(beansOfType);
    }
}
