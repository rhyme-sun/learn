package learn.java.jvm.classloader.isolation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Objects;

/**
 * IsolationClassLoader.
 */
public class IsolationClassLoader extends ClassLoader {

    private final URLClassLoader urlClassLoader;

    public IsolationClassLoader(String[] paths) {
        Objects.requireNonNull(paths);
        try {
            URL[] urls = new URL[paths.length];
            for (int i = 0; i < paths.length; i++) {
                URL jarUrl = new URL("file:\\" + paths[i]);
                urls[i] = jarUrl;
            }
            urlClassLoader = new URLClassLoader(urls);
        } catch (Exception e) {
            throw new RuntimeException("类加载器初始化失败，jar 路径：" + Arrays.toString(paths), e);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class result = null;
        try {
            // 使用 PlatformClassLoader 加载 JDK 本身需要的类
            result = ClassLoader.getPlatformClassLoader().loadClass(name);
        } catch (Exception ignore) {
        }
        if (Objects.nonNull(result)) {
            return result;
        }
        String path = name.replace('.', '/').concat(".class");
        try (InputStream in = urlClassLoader.getResourceAsStream(path)) {
            if (Objects.nonNull(in)) {
                byte[] buffer = new byte[in.available()];
                in.read(buffer);
                return defineClass(name, buffer, 0, buffer.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}