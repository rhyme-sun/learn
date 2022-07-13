package learn.spring.ioc.refresh.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * MyConfigurationClass.
 */
@Configuration
//@Component
public class MyConfigurationClass {

    @Bean
    public BeanByAnnotation innerBeanByAnnotation() {
        return new BeanByAnnotation();
    }

    @Bean
    public BeanByAnnotation instanceBeanByAnnotation() {
        // @Bean 在 @Configuration 下遵循 JavaConfig 语义，调用另一个 @Bean 方法会被增强，从 BeanFactory 中获取
        // @Bean 在 @Component 下属于 Lite Mode，不遵循上述语义，下面方法只是类内部方法平调，会创建一个新的对象
        BeanByAnnotation innerBeanByAnnotation = innerBeanByAnnotation();
        // 但如果调用静态方法，还是会创建新的 bean，不遵循上述语义
        staticBeanByAnnotation();

        BeanByAnnotation beanByAnnotation = new BeanByAnnotation();
        beanByAnnotation.setInner(innerBeanByAnnotation);
        return beanByAnnotation;
    }

    @Bean
    @Scope()
    public static BeanByAnnotation staticBeanByAnnotation() {
        return new BeanByAnnotation();
    }
}
