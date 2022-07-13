package learn.spring.bean.alias;

import learn.spring.bean.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * BeanAliasExample.
 */
public class BeanAliasExample {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-alias-context.xml");

        final User aliasUser = beanFactory.getBean("aliasUser", User.class);
        final User user = beanFactory.getBean("user", User.class);
        // true
        System.out.println(aliasUser == user);
    }
}
