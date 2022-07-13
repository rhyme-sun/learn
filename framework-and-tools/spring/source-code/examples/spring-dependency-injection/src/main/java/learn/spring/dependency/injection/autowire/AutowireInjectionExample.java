package learn.spring.dependency.injection.autowire;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * AutowireInjectionExample.
 */
public class AutowireInjectionExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:META-INF/autowired.xml");
        final AutowireByType autowireByType = ac.getBean("autowireByType", AutowireByType.class);
        System.out.println("Object: " + autowireByType.getObject());

        System.out.println("AutowiredBean: " + autowireByType.getAutowiredBean());
        System.out.println("AllBeanMap: " + autowireByType.getAllBeanMap());
        System.out.println("BeanMap: " + autowireByType.getBeanMap());
        System.out.println("AllBeanList: " + autowireByType.getAllBeanList());
        System.out.println("BeanList: " + autowireByType.getBeanList());
        System.out.println("Properties: " + autowireByType.getProperties());
    }
}
