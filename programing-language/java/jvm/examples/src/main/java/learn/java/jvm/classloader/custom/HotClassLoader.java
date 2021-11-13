package learn.java.jvm.classloader.custom;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;

/**
 * 动态的加载和卸载类。
 * <br>
 * 动态加载：使用{@link WatchService}监听文件（.class）的变化并动态加载类。
 * // TODO ... 还未实现
 */
public class HotClassLoader extends ClassLoader {

    private final URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

    /**
     * 加载类的根路径
     */
    private final String rootDir;

    public HotClassLoader(String rootDir) {
        Objects.requireNonNull(rootDir, "RootDir can not be null!");
        this.rootDir = rootDir;
        listen(rootDir);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    /**
     * 监听本地文件变化
     *
     * @param listenDir 监听目录
     */
    private void listen(String listenDir) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(listenDir);
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                    String fileName = event.context().toString();
                    reload(fileName);
                }
                key.reset();
            }
        } catch (Exception e) {
            throw new RuntimeException("动态类加载器初始化失败", e);
        }

    }

    /**
     * 重新加载指定的类
     *
     * @param name 变更文件
     */
    private void reload(String name) {
        try {
            String path = this.rootDir + File.separatorChar + name.replace('.', File.separatorChar).concat(".class");
            URL xarUrl = new URL(path);

            URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURL.setAccessible(true);
            addURL.invoke(urlClassLoader, xarUrl);
        } catch (Exception e) {
           throw new RuntimeException("重新加载类失败", e);
        }
    }
}
