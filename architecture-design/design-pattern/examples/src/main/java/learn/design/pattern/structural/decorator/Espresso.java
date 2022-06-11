package learn.design.pattern.structural.decorator;

/**
 * 浓缩咖啡
 */
public class Espresso extends Beverage {

	public Espresso () {
		this.description = "浓缩咖啡";
	}

	@Override
	public double cost() {
		return 1.4;
	}
}
