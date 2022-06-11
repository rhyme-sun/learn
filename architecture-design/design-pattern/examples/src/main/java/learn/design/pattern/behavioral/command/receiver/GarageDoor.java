package learn.design.pattern.behavioral.command.receiver;

/**
 * 车库门
 */
public class GarageDoor {

	public void up() {
		System.out.println("打开车库门");
	}

	public void down() {
		System.out.println("关掉车库门");
	}
}
