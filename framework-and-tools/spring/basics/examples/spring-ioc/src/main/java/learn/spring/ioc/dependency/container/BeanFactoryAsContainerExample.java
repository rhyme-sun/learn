package learn.spring.ioc.dependency.container;

import learn.spring.ioc.dependency.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 使用 BeanFactory 作为 Spring IoC 容器
 *
 * BeanFactoryAsContainerExample.
 */
public class BeanFactoryAsContainerExample {

    public static void main(String[] args) {
        // 创建 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:META-INF/dependency-lookup-context.xml";
        final int count = reader.loadBeanDefinitions(location);
        System.out.println("加载 BeanDefinition 个数：" + count);

        final User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }
}
