package learn.design.pattern.behavioral.observer.sysn;

import java.util.ArrayList;
import java.util.List;

/**
 * 天气数据，主题的具体实现类
 */
public class WeatherData implements Subject {

	private List<Observer> observerList = new ArrayList<>();
	private Float temperature;
	private Float humidity;
	private Float pressure;

	@Override
	public void registerObserver(Observer observer) {
		observerList.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observerList.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observerList) {
			observer.update(temperature, humidity, pressure);
		}
	}

	/**
	 *当从气象站得到更新观测值时，我们通知观察者
	 */
	public void measurementsChanged() {
		notifyObservers();
	}

	/**
	 * 从气象站得到更新观测值
	 */
	public void setMeasurements(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		measurementsChanged();
	}

	public Float getTemperature() {
		return temperature;
	}

	public Float getHumidity() {
		return humidity;
	}

	public Float getPressure() {
		return pressure;
	}
}
