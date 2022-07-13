package learn.java.jvm.classloading;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassLoadingProcedure.
 */
@Slf4j
public class ClassLoadingProcedure {

    public static void main(String[] args) {
        // 2
        log.info("{}", Foo.count);
        // 3
        log.info("{}", Bar.count);
    }
}

class Foo {

    public static Foo foo = new Foo();
    /**
     * count 的值由 0 -> 1 -> 2
     */
    public static int count = 2;

    private Foo() {
        count++;
    }
}

class Bar {
    /**
     * count 的值由 0 -> 2 -> 3
     */
    public static int count = 2;
    public static Bar bar = new Bar();

    private Bar() {
        count++;
    }
}