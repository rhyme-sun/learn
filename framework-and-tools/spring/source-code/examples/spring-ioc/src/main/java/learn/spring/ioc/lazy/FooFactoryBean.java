package learn.spring.ioc.lazy;

import org.springframework.beans.factory.FactoryBean;

/**
 * FooFactoryBean.
 */
public class FooFactoryBean implements FactoryBean<Foo> {

    @Override
    public Foo getObject() throws Exception {
        return new Foo();
    }

    @Override
    public Class<?> getObjectType() {
        return Foo.class;
    }

    @Override
    public boolean isSingleton() {
        //return true;
        return false;
    }
}
