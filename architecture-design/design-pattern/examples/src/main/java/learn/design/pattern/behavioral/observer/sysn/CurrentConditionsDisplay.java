package learn.design.pattern.behavioral.observer.sysn;

/**
 * 当前气象信息展示公告板
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {

	private float temperature;

	private float humidity;

	private float pressure;

	private Subject subject;

	public CurrentConditionsDisplay(Subject subject) {
		this.subject = subject;
		subject.registerObserver(this);
	}

	@Override
	public void display() {
		System.out.println("当前气象信息展示公告板");
		System.out.println("temperature:" + this.temperature);
		System.out.println("humidity:" + this.humidity);
		System.out.println("pressure:" + this.pressure);
		System.out.println();
	}

	@Override
	public void update(float temp, float humidity, float pressure) {
		this.temperature = temp;
		this.humidity = humidity;
		this.pressure = pressure;
		display();
	}
}
