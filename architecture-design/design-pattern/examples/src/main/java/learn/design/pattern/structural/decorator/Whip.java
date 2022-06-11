package learn.design.pattern.structural.decorator;

/**
 * 气泡调料
 */
public class Whip extends CondimentDecorator {

	private Beverage beverage;

	public Whip (Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public double cost() {
		return beverage.cost() + 0.4;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + "，加气泡";
	}
}
