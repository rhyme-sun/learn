package learn.design.pattern.behavioral.strategy;

/**
 * 用翅膀飞
 */
public class FlyWithWings implements FlyBehavior {

	@Override
	public void fly() {
		System.out.println("用翅膀飞");
	}

}
