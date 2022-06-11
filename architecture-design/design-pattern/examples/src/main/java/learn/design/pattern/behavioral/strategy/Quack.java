package learn.design.pattern.behavioral.strategy;

/**
 * 嘎嘎叫
 */
public class Quack implements  QuackBehavior {

	@Override
	public void quack() {
		System.out.println("嘎嘎叫");
	}

}
