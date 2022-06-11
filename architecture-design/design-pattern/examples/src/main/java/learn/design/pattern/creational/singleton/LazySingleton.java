package learn.design.pattern.creational.singleton;

/**
 * 懒汉式单例
 * <p>
 * 懒汉式单例支持延迟加载，且使用 synchronized 同步锁保证在并发条件下对象实例被唯一创建
 */
public class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        synchronized (LazySingleton.class) {
            if (instance == null) {
                // lazy initialize 延迟实例化
                instance = new LazySingleton();
            }
        }
        return instance;
    }
}
