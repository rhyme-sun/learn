package learn.java.jvm.classloader;

import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * JDK 8 下执行：
 *
 * 类加载范围，sun.boot.class.path、java.ext.dirs、java.class.path 属性名可再{@link sun.misc.Launcher}中查看到。
 */
public class ClassLoaderScope {

    public static void main(String[] args) {
        printClassLoaderScope1();
        System.out.println("-------------------------------------------------------------------");
        printClassLoaderScope2();
    }

    private static void printClassLoaderScope1() {
        System.out.println("BootstrapClassLoader 加载范围：");
        String bootPath = System.getProperty("sun.boot.class.path");
        System.out.println(bootPath.replaceAll(";", System.lineSeparator()));
        System.out.println();

        System.out.println("ExtClassLoader 加载的范围：");
        String extPath = System.getProperty("java.ext.dirs");
        System.out.println(extPath.replaceAll(";", System.lineSeparator()));
        System.out.println();

        System.out.println("AppClassLoader 加载的范围：");
        String appPath = System.getProperty("java.class.path");
        System.out.println(appPath.replaceAll(";", System.lineSeparator()));
    }

    private static void printClassLoaderScope2() {
        System.out.println("BootstrapClassLoader 加载范围：");
        //URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        //Arrays.stream(urLs).forEach(System.out::println);
        System.out.println();

        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassLoader = appClassLoader.getParent();

        System.out.println("ExtClassLoader 加载范围：");
        printClassLoaderScope(extClassLoader);
        System.out.println();

        System.out.println("BootstrapClassLoader 加载范围：");
        printClassLoaderScope(appClassLoader);
        System.out.println();
    }

    private static void printClassLoaderScope(ClassLoader classLoader) {
        Object ucp = insightField(classLoader, "ucp");
        Object path = insightField(ucp, "path");
        ArrayList paths = (ArrayList) path;
        paths.stream().forEach(System.out::println);
    }

    private static Object insightField(Object obj, String fName) {
        try {
            Field f = null;
            if (obj instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fName);
            } else {
                f = obj.getClass().getDeclaredField(fName);
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
