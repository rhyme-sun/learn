package learn.design.pattern.structural.decorator;

/**
 * 混合咖啡
 */
public class HouseBlend extends Beverage {

	public HouseBlend() {
		this.description = "混合咖啡";
	}

	@Override
	public double cost() {
		return 2.5;
	}
}
