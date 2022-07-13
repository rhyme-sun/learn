package learn.spring.ioc.selfeditor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SelfEditorExample
 */
public class SelfEditorExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:self-property-editor.xml");
        System.out.println(ac.getBean("simon"));
    }
}
