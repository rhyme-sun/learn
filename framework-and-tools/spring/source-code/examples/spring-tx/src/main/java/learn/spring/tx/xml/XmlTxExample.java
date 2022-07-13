package learn.spring.tx.xml;

import learn.spring.tx.xml.service.BookService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * XmlTxExample.
 */
public class XmlTxExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:tx-jdbc.xml");
        final BookService bookService = ac.getBean("bookService", BookService.class);

        bookService.checkout("simon", 1);
    }
}
