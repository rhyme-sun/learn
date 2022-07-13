package learn.spring.ioc.refresh.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * AnnotationContextExample
 */
public class AnnotationContextExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfigurationClass.class);

        System.out.println(ac.getBean(MyConfigurationClass.class).getClass());

        System.out.println(ac.getBean("instanceBeanByAnnotation"));
        System.out.println(ac.getBean("staticBeanByAnnotation"));
        System.out.println(ac.getBean("staticBeanByAnnotation"));

        System.out.println();
        final BeanByAnnotation bean = ac.getBean("instanceBeanByAnnotation", BeanByAnnotation.class);
        System.out.println(bean.getInner());
        System.out.println(ac.getBean("innerBeanByAnnotation"));
        ac.close();
    }
}
