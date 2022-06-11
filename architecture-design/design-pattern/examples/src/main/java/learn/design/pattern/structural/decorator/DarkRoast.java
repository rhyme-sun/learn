package learn.design.pattern.structural.decorator;

/**
 * 深烘焙咖啡
 */
public class DarkRoast extends Beverage {

	public DarkRoast() {
		this.description = "深烘焙咖啡";
	}

	@Override
	public double cost() {
		return 1.1;
	}
}
