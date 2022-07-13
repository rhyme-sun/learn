package learn.spring.ioc.config;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ConfigClassExample.
 *
 * Debug ConfigurationClassPostProcessor.
 */
public class ConfigClassExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:config.xml");
        System.out.println();
    }
}
