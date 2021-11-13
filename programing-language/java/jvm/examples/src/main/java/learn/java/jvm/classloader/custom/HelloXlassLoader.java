package learn.java.jvm.classloader.custom;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Hello.xlass 类加载器，加载 Hello.xlass。
 * Hello.xlasss，Hello.class 每个字节用 255 相减得到的文件。
 */
public class HelloXlassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] encryption = loadClassData(name);
        if (encryption != null) {
            byte[] decryption = new byte[encryption.length];
            for (int i = 0; i < encryption.length; i++) {
                decryption[i] = (byte) (255 - encryption[i]);
            }
            return defineClass(name, decryption, 0, decryption.length);
        }
        return super.findClass(name);
    }

    private byte[] loadClassData(String className) {
        try {
            return Files.readAllBytes(Paths.get("xar/Hello.xlass"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String className = "Hello";
        String methodName = "hello";

        HelloXlassLoader loader = new HelloXlassLoader();
        Class<?> loaderClass = loader.findClass(className);
        final Constructor<?> declaredConstructor = loaderClass.getDeclaredConstructor();
        final Object hello = declaredConstructor.newInstance();
        Method method = loaderClass.getMethod(methodName);
        method.invoke(hello);
    }
}
