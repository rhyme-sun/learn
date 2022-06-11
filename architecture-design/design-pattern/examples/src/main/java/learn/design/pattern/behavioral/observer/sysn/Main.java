package learn.design.pattern.behavioral.observer.sysn;

/**
 * 测试
 */
public class Main {

	public static void main(String[] args) {
		// 创建一个主题
		WeatherData subject = new WeatherData();
		// 创建一个观察者，并注册主题
		CurrentConditionsDisplay observer1 = new CurrentConditionsDisplay(subject);
		HeatIndexDisplay observer2 = new HeatIndexDisplay(subject);

		// 数据更新
		subject.setMeasurements(80, 80, 80);
	}
}
