package learn.spring.dependency.injection.constructor;

import learn.spring.dependency.domain.UserHolder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ApiDependencyConstructorInjectionExample.
 */
public class ApiDependencyConstructorInjectionExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        loadXmlBeanDefinition(applicationContext);
        // 创建 UserHolder BeanDefinition
        final BeanDefinition userHolderBeanDefinition = createUserHolderBeanDefinition();
        // 注册 UserHolder BeanDefinition
        applicationContext.registerBeanDefinition("userHolder", userHolderBeanDefinition);
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

    private static BeanDefinition createUserHolderBeanDefinition() {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        // 依赖注入
        beanDefinitionBuilder.addConstructorArgReference("user");
        return beanDefinitionBuilder.getBeanDefinition();
    }
}
