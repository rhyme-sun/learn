package learn.design.pattern.structural.decorator;

/**
 * for test.
 */
public class Main {

	public static void main(String[] args) {

		// 订单1：一杯什么都不加的混合咖啡
		Beverage beverage1 = new DarkRoast();
		printCoffeeInfo(beverage1);
		// 订单2：加豆浆气泡的浓缩咖啡
		Beverage beverage2 = new Espresso();
		beverage2 = new Soy(beverage2);
		beverage2 = new Whip(beverage2);
		printCoffeeInfo(beverage2);
		// 订单3：加牛奶气泡双份摩卡的深烘焙咖啡
		Beverage beverage3 = new DarkRoast();
		beverage3 = new Milk(beverage3);
		beverage3 = new Mocha(beverage3);
		beverage3 = new Mocha(beverage3);
		beverage3 = new Whip(beverage3);
		printCoffeeInfo(beverage3);
	}

	private static void printCoffeeInfo(Beverage beverage) {
		System.out.println(String.format("咖啡：[%s]，价格：[%s]", beverage.getDescription(), beverage.cost()));
	}
}
