package learn.design.pattern.structural.decorator;

/**
 * 饮料抽象类
 */
public abstract class Beverage {

	public String description = "unknown description";

	/**
	 * 获得饮料价格
	 */
	public abstract double cost();

	/**
	 * 获得饮料描述
	 */
	public String getDescription() {
		return this.description;
	}
}
