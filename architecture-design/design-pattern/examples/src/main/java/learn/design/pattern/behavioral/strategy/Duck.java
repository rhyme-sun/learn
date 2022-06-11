package learn.design.pattern.behavioral.strategy;

/**
 * 鸭子父类
 */
public abstract class Duck {

	protected QuackBehavior quackBehavior;

	protected FlyBehavior flyBehavior;

	abstract void display();

	public void quackBehavior() {
		quackBehavior.quack();
	}

	public void flyBehavior() {
		flyBehavior.fly();
	}
}
