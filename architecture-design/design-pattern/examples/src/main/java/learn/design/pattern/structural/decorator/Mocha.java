package learn.design.pattern.structural.decorator;

/**
 * 摩卡
 */
public class Mocha extends CondimentDecorator {

	private Beverage beverage;

	public Mocha (Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public double cost() {
		return beverage.cost() + 0.9;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + "，加摩卡";
	}

}
