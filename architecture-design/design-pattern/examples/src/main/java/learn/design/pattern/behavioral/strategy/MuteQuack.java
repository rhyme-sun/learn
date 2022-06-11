package learn.design.pattern.behavioral.strategy;

/**
 * 不会叫
 */
public class MuteQuack implements QuackBehavior {

	@Override
	public void quack() {
		System.out.println("不会叫");
	}

}
