package learn.spring.ioc.instantiation.supplier;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SupplierBeanExample.
 */
public class SupplierInstantiationExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:supplier.xml");
        System.out.println(ac.getBean("supplierBean"));
    }
}
