package learn.design.pattern.structural.decorator;

/**
 * 低卡咖啡
 */
public class Decaf extends Beverage {

	public Decaf() {
		this.description = "低卡咖啡";
	}

	@Override
	public double cost() {
		return 3.5;
	}
}
