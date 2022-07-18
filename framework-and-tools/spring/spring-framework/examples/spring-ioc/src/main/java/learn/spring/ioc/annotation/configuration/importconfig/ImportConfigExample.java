package learn.spring.ioc.annotation.configuration.importconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ImportConfigExample.
 */
public class ImportConfigExample {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println(ctx.getBeansOfType(ImportedBean.class));
    }
}
