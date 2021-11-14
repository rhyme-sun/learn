package learn.java.jvm.classloader.isolation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassLoadingIsolationExample.
 * 需要将 xar 下的 jar 包手动引入
 */
@Slf4j
public class ClassLoadingIsolationExample {

    public static void main(String[] args) {
        example2();
    }

    /*private static void example1() {
        Foo foo = new Foo();
        foo.foo();

        Bar bar = new Bar();
        bar.bar();
    }*/

    private static void example2() {
        try {
            String[] fooPaths = new String[]{
                    "E:\\LearnProjects\\learn\\programing-language\\java\\jvm\\examples\\xar\\caseutil-1.0.0.jar",
                    "E:\\LearnProjects\\learn\\programing-language\\java\\jvm\\examples\\xar\\foo-1.0-SNAPSHOT.jar"
            };
            IsolationClassLoader fooLoader = new IsolationClassLoader(fooPaths);
            final Class<?> fooClass = fooLoader.loadClass("learn.foo.Foo");
            final Object foo = fooClass.getDeclaredConstructor().newInstance();
            final Method fooMethod = fooClass.getDeclaredMethod("foo");
            fooMethod.invoke(foo);

            String[] barPaths = new String[]{
                    "E:\\LearnProjects\\learn\\programing-language\\java\\jvm\\examples\\xar\\caseutil-1.0.1.jar",
                    "E:\\LearnProjects\\learn\\programing-language\\java\\jvm\\examples\\xar\\bar-1.0-SNAPSHOT.jar"
            };
            IsolationClassLoader barLoader = new IsolationClassLoader(barPaths);
            final Class<?> barClass = barLoader.loadClass("learn.bar.Bar");
            final Object bar = barClass.getDeclaredConstructor().newInstance();
            final Method barMethod = barClass.getDeclaredMethod("bar");
            barMethod.invoke(bar);

        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}