package learn.design.pattern.structural.decorator;

/**
 * 豆浆
 */
public class Soy extends CondimentDecorator {

	private Beverage beverage;

	public Soy (Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public double cost() {
		return beverage.cost() + 0.6;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + "，加豆浆";
	}
}
