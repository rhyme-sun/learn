package learn.design.pattern.creational.singleton;

/**
 * 饿汉式单例
 *
 * 饿汉式单例模式，在类加载的过程中实例就被初始化好了，且实例在创建过程中是线程安全的。这样的实现方式不支持延迟加载。
 */
public class HungrySingleton {

	/**
	 * 在静态初始化器（static initialize）中创建单例，static 保证了线程安全
	 */
	private static final HungrySingleton instance = new HungrySingleton();

	private HungrySingleton() {
	}

	public static HungrySingleton getInstance() {
		return instance;
	}
}
