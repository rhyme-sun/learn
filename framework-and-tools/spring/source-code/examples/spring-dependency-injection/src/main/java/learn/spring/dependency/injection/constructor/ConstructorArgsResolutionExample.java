package learn.spring.dependency.injection.constructor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ConstructorArgsResolutionExample.
 */
public class ConstructorArgsResolutionExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/constructor-arg-resolution.xml");

        System.out.println(context.getBean("beanOne", ThingOne.class));
        System.out.println(context.getBean("beanOne", ThingOne.class).getThingTwo());
        System.out.println(context.getBean("beanOne", ThingOne.class).getThingThree());
    }
}
