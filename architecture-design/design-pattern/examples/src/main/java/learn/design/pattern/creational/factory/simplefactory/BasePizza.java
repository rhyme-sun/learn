package learn.design.pattern.creational.factory.simplefactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 披萨抽象类，不同风格的披萨继承该类并重新披萨的准备原料方法
 */
public abstract class BasePizza {

	String name;

	/**
	 * 披萨原料
	 */
	List<String> ingredients = new ArrayList<>();

	public abstract void prepare();

	void bake() {
		System.out.println("Baking pizza...");
	}

	void cut() {
		System.out.println("Cutting pizza...");
	}

	void box() {
		System.out.println("Boxing pizza...");
	}

	void progress() {
		prepare();
		bake();
		cut();
		cut();
	}

	public static class PizzaType {

		public static final String BEEF_PIZZA = "beef";
		public static final String DURIAN_PIZZA = "durian";
	}
}
