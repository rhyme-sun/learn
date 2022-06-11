package learn.design.pattern.creational.singleton;

/**
 * 使用静态内部类延迟初始化单例
 * <br>
 * 静态内部类，外部类被加载时，不会加载{@link SingletonHolder}，只有 {@link StaticInnerClassSingleton#getInstance()}
 * 被调用时，才会被加载并创建 instance，这样便做到了延迟加载。
 */
public class StaticInnerClassSingleton {

    private StaticInnerClassSingleton() {
    }

    public static StaticInnerClassSingleton getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 当 StaticInnerClassSingleton 被加载时，SingletonHolder不会被加载
     */
    public static class SingletonHolder {
        private static StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
    }
}
