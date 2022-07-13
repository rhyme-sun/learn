package learn.spring.dependency.lookup;

import java.util.Arrays;

import learn.spring.dependency.domain.User;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * HierarchicalDependencyExample.
 */
public class HierarchicalDependencyExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(HierarchicalDependencyExample.class);
        applicationContext.refresh();

        final ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("ParentBeanFactory: " + beanFactory.getParentBeanFactory());

        // 设置 ParentBeanFactory
        beanFactory.setParentBeanFactory(createParentBeanFactory());
        System.out.println("ParentBeanFactory: " + beanFactory.getParentBeanFactory());

        System.out.println("ContainsBean: " + beanFactory.containsBean("user"));
        System.out.println("ContainsLocalBean: " + beanFactory.containsLocalBean("user"));

        final Object user = beanFactory.getBean("user");
        System.out.println(user);

        final String[] userBeanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, User.class);
        System.out.println("User BeanNames: " + Arrays.toString(userBeanNames));

        applicationContext.close();
    }

    private static ConfigurableListableBeanFactory createParentBeanFactory() {
        // 创建 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:META-INF/dependency-lookup-context.xml";
        reader.loadBeanDefinitions(location);
        return beanFactory;
    }
}
