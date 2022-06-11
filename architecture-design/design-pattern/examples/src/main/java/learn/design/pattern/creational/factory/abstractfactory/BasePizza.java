package learn.design.pattern.creational.factory.abstractfactory;

import java.util.ArrayList;

/**
 * 披萨抽象类，不同风格的披萨继承该类并重新披萨的准备原料方法
 */
public abstract class BasePizza {

	/**
     * 披萨名称
	 */
	String name;

	/**
     * 披萨原料
	 */
	ArrayList<String> ingredients = new ArrayList<String>();

	/**
	 * 准备原料
	 */
	public abstract void prepare();

	/**
	 * 烤
	 */
	void bake() {
		System.out.println("Baking pizza...");
	}

	/**
	 * 切
	 */
	void cut() {
		System.out.println("Cutting pizza...");
	}

	/**
	 * 包装
	 */
	void box() {
		System.out.println("Boxing pizza...");
	}

	/**
	 * 制作流程
	 */
	void progress() {
		prepare();
		bake();
		cut();
		cut();
	}
}
