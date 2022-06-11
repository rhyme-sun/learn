package learn.design.pattern.behavioral.command.receiver;

/**
 * 空调
 */
public class Thermostat {

	public void on() {
		System.out.println("打开空调");
	}

	public void off() {
		System.out.println("关闭空调");
	}

	public void heatUp() {
		System.out.println("升温");
	}

	public void coolDown() {
		System.out.println("降温");
	}
}
