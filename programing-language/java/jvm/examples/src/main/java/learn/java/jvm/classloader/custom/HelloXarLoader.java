package learn.java.jvm.classloader.custom;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

/**
 * 使用 URLClassLoader 加载指定路径的 jaro
 * <p>
 * Tips:
 * <br>
 * hello.xar 构建命令：jar cvf hello.xar Hello.xlass。
 * <br>
 */
public class HelloXarLoader extends ClassLoader {

   private final URLClassLoader urlClassLoader;

    public HelloXarLoader(String path) {
        try {
            URL xarUrl = new URL("file:\\" + path);
            urlClassLoader = new URLClassLoader(new URL[]{xarUrl});
        } catch (Exception e) {
            throw new RuntimeException("hello.xar 类加载器初始化失败!", e);
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = name.replace('.', '/').concat(".xlass");
        try (InputStream in = urlClassLoader.getResourceAsStream(path)) {
            if (Objects.nonNull(in)) {
                byte[] encryption = new byte[in.available()];
                in.read(encryption);

                byte[] decryption = new byte[encryption.length];
                for (int i = 0; i < encryption.length; i++) {
                    decryption[i] = (byte) (255 - encryption[i]);
                }
                return defineClass(name, decryption, 0, decryption.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    public static void main(String[] args) throws Exception {
        String path = "F:\\LearnProjects\\learn\\programing-language\\java\\jvm\\examples\\xar\\hello.xar";
        String className = "Hello";
        String methodName = "hello";

        HelloXarLoader loader = new HelloXarLoader(path);
        Class<?> loaderClass = loader.findClass(className);

        Object hello = loaderClass.newInstance();
        Method method = loaderClass.getMethod(methodName);
        method.invoke(hello);
    }
}
