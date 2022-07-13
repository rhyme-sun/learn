package learn.spring.dependency.injection.setter;

import learn.spring.dependency.domain.UserHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * XmlDependencySetterInjectionExample.
 */
public class XmlDependencySetterInjectionExample {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String path = "classpath:/META-INF/dependency-setter-injection-context.xml";
        reader.loadBeanDefinitions(path);

        final UserHolder bean = beanFactory.getBean(UserHolder.class);
        System.out.println(bean);
    }
}
