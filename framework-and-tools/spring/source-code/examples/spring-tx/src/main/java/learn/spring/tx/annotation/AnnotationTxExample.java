package learn.spring.tx.annotation;

import java.util.ArrayList;

import learn.spring.tx.annotation.config.TransactionConfig;
import learn.spring.tx.annotation.service.BookService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationTxExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TransactionConfig.class);
        applicationContext.refresh();

        System.out.println(applicationContext.getBean("bookService", BookService.class));
    }
}
