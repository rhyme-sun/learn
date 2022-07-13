package learn.spring.ioc.annotation.bean.returninterface;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ReturnInterfaceTypeExample.
 */
public class ReturnInterfaceTypeExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(AppConfig.class);
        ac.register(TransferController.class);
        ac.refresh();
        System.out.println(ac.getBean("transferService", TransferService.class).getClass());
        System.out.println(ac.getBean("transferService", TransferServiceImpl.class).getClass());
        System.out.println();

        System.out.println(ac.getBean("transferServiceImpl", TransferService.class).getClass());
        System.out.println(ac.getBean("transferServiceImpl", TransferServiceImpl.class).getClass());

        final TransferController bean = ac.getBean(TransferController.class);
        ac.close();
    }
}
