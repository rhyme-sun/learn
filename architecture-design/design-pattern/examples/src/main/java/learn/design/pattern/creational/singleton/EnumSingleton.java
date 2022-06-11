package learn.design.pattern.creational.singleton;

/**
 * 使用枚举实现单例
 * 枚举单例模式，这种实现方式通过 Java 枚举类型本身的特性，保证了实例创建的线程安全性和唯一性。
 */
public enum  EnumSingleton {

	INSTANCE;

	public static EnumSingleton getInstance() {
		return INSTANCE;
	}
}
