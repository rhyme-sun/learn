package learn.spring.ioc.lazy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

/**
 * FooObjectFactory.
 */
public class FooObjectFactory implements ObjectFactory<Foo> {

    @Override
    public Foo getObject() throws BeansException {
        return new Foo();
    }
}
