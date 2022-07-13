package learn.spring.dependency.injection;

import learn.spring.dependency.domain.Foo;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * DependencyInjectionConvertExample.
 *
 * Bean 属性注入类型转换：枚举，Resource 对象
 */
public class DependencyInjectionConvertExample {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String path = "classpath:/META-INF/dependency-injection-convert-context.xml";
        reader.loadBeanDefinitions(path);

        final Foo bean = beanFactory.getBean(Foo.class);
        System.out.println(bean);
    }
}
