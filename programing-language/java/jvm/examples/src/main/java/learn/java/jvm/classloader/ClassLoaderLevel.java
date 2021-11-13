package learn.java.jvm.classloader;

import lombok.extern.slf4j.Slf4j;

/**
 * 类加载器层次结构
 */
@Slf4j
public class ClassLoaderLevel {

    public static void main(String[] args) {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        // AppClassLoader：自定义类加载其本身是由 AppClassLoader 加载的
        log.info("CustomClassLoader's classLoader: {}", customClassLoader.getClass().getClassLoader());
        // AppClassLoader：自定义类加载的父加载器是 AppClassLoader
        log.info("CustomClassLoader's parent loader: {}", customClassLoader.getParent());
        // AppClassLoader：定义在 classpath 里的类是由 AppClassLoader 加载的
        ClassLoader appClassLoader = ClassLoaderLevel.class.getClassLoader();
        log.info("ClassLoaderLevel's classLoader: {}", appClassLoader);

        // ExtClassLoader：AppClassLoader 的父加载器是 ExtClassLoader
        // JDK 9 之后是 AppClassLoader 的父加载器是 PlatformClassLoader
        ClassLoader extClassLoader = appClassLoader.getParent();
        log.info("AppClassLoader's parent loader: {}", extClassLoader);

        // BootstrapClassLoader(null)：ExtClassLoader 的父加载器为 BootstrapClassLoader，但使用 Java API 获取不到
        log.info("ExtClassLoader's parent loader: {}", extClassLoader.getParent());
        // NullPointerException
        //System.out.println(extClassLoader.getParent().getParent());

        // BootstrapClassLoader(null)：AppClassLoader 加载器类的加载器为 BootstrapClassLoader
        log.info("AppClassLoader's classLoader: {}", appClassLoader.getClass().getClassLoader());

        // BootstrapClassLoader(null)：ExtClassLoader 加载器类的加载器为 BootstrapClassLoader
        log.info("ExtClassLoader's classLoader: {}", extClassLoader.getClass().getClassLoader());

        // AppClassLoader：可使用 ClassLoader#getSystemClassLoader 方法获取到 AppClassLoader
        log.info("ClassLoader.getSystemClassLoader: {}", ClassLoader.getSystemClassLoader());
        // PlatformClassLoader：可使用 ClassLoader#getPlatformClassLoader 方法获取到 PlatformClassLoader
        log.info("ClassLoader.getPlatformClassLoader: {}", ClassLoader.getPlatformClassLoader());
    }
}

class CustomClassLoader extends ClassLoader {

}
