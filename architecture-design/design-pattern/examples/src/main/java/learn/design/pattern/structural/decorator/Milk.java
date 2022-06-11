package learn.design.pattern.structural.decorator;

/**
 * 牛奶
 */
public class Milk extends CondimentDecorator {

	private Beverage beverage;

	public Milk (Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public double cost() {
		return beverage.cost() + 1.2;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + "，加牛奶";
	}
}
