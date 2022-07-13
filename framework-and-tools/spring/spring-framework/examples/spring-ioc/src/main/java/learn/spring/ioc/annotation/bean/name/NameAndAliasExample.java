package learn.spring.ioc.annotation.bean.name;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * NameAndAliasExample.
 */
public class NameAndAliasExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(AppConfig.class);
        ac.refresh();
        System.out.println(ac.getBean("foo1") ==  ac.getBean("foo2"));
        System.out.println(ac.getBean("foo2") ==  ac.getBean("foo3"));
    }
}
