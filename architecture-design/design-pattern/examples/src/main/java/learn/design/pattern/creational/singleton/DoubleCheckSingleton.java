package learn.design.pattern.creational.singleton;

/**
 * 双重检测
 *
 * @apiNote 双重检测单例模式，只要 instance 被创建之后，即便再调用 getInstance() 函数也不会再进入到加锁逻辑中了。
 * 使用 volatile 是为了解决指令重排问题，但高版本的 JDK 没有此问题，所以 volatile 可以不用。
 */
public class DoubleCheckSingleton {

    /**
     * 因为指令重排序，可能会导致对象被 new 出来，然后赋值给 instance ，还没来得及执行构造函数中的代码逻辑，就被另一个线程使用了，
     * 这时候另外的线程就会使用到类半初始化的属性值，这显然是不符合我们预期的，使用 volatile 关键字是为了静止指令重排。
     * 对 volatile 关键字修饰的变量的写操作对读操作是可见的。
     * <p>
     */
    private volatile static DoubleCheckSingleton instance;

    private DoubleCheckSingleton() {
    }

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
