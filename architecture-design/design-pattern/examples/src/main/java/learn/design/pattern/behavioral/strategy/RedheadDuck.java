package learn.design.pattern.behavioral.strategy;

/**
 * 红头鸭
 */
public class RedheadDuck extends Duck {

	public RedheadDuck() {
		super.flyBehavior = new FlyWithWings();
		super.quackBehavior = new Quack();
	}

	@Override
	void display() {
		System.out.println("这是一只红头鸭");
	}
}
