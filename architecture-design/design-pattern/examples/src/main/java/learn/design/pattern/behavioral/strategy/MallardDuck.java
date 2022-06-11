package learn.design.pattern.behavioral.strategy;

/**
 * 绿头鸭
 *
 */
public class MallardDuck extends Duck {

	public MallardDuck() {
		super.flyBehavior = new FlyWithWings();
		super.quackBehavior = new Quack();
	}

	@Override
	void display() {
		System.out.println("这是一只绿头鸭");
	}
}
