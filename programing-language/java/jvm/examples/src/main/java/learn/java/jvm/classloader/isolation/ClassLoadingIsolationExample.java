package learn.java.jvm.classloader.isolation;

import learn.bar.Bar;
import learn.foo.Foo;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassLoadingIsolationExample.
 */
@Slf4j
public class ClassLoadingIsolationExample {

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.foo();

        Bar bar = new Bar();
        bar.bar();
    }
}