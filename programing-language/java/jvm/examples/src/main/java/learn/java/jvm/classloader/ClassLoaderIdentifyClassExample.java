package learn.java.jvm.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ClassLoaderIdentifyClassExample.
 * Class 对象是否相等首先取决于加载其的 ClassLoader 是否是同一个。
 */
public class ClassLoaderIdentifyClassExample {

    public static void main(String[] args) throws Exception {

        ClassLoader myClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1)+".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj = myClassLoader.loadClass("learn.java.jvm.classloader.ClassLoaderIdentifyClassExample")
                .getConstructor().newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderIdentifyClassExample); // false

        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);

        Set<List<Integer>> set = new HashSet<>();
        set.add(list1);
        set.add(list2);
        System.out.println(set);
     }
}
