package learn.spring.ioc.annotation.bean.returninterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TransferController.
 */
@Component
public class TransferController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private TransferServiceImpl transferServiceImpl;
}
