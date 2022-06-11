package learn.design.pattern.behavioral.command.receiver;

/**
 * 热水浴缸
 */
public class Hottub {

	public void addWater() {
		System.out.println("向浴缸里加水");
	}

	public void heatWater() {
		System.out.println("加热水");
	}

	public void releaseWater() {
		System.out.println("放水");
	}
}
