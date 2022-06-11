package learn.design.pattern.behavioral.strategy;

/**
 * 测试
 */
public class Main {

	public static void main(String[] args) {
		RedheadDuck redheadDuck = new RedheadDuck();
		redheadDuck.display();
		redheadDuck.flyBehavior();
		redheadDuck.quackBehavior();

		MallardDuck mallardDuck = new MallardDuck();
		mallardDuck.display();
		mallardDuck.flyBehavior();
		mallardDuck.quackBehavior();
	}
}
