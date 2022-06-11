package learn.design.pattern.behavioral.strategy;

/**
 * 吱吱叫
 */
public class Squack implements QuackBehavior {

	@Override
	public void quack() {
		System.out.println("吱吱叫");
	}
}
