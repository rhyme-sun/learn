package learn.spring.ioc.instantiation.supplier;

import java.util.function.Supplier;

/**
 * MyBeanSupplier
 */
public class MyBeanSupplier implements Supplier<SupplierBean> {

    @Override
    public SupplierBean get() {
        return new SupplierBean();
    }
}
