package learn.design.pattern.behavioral.observer.sysn;

/**
 * 酷热指数公告板
 */
public class HeatIndexDisplay implements Observer, DisplayElement {

	private float heatindex;

	private Subject subject;

	public HeatIndexDisplay(Subject subject) {
		this.subject = subject;
		subject.registerObserver(this);
	}

	@Override
	public void display() {
		System.out.println("酷热指数公告板");
		System.out.println("heatIndex:" + this.heatindex);
		System.out.println();
	}

	@Override
	public void update(float temp, float humidity, float pressure) {
		calculateHeatIndex(temp, humidity);
		display();
	}

	/**
	 * 计算酷热指数，体感指数
	 */
	private void calculateHeatIndex(float T, float RH) {
		this.heatindex = 16.923f + 1.85212f * 10 - 1 * T + 5.37941f * RH - 1.00254f * 10 - 1
				* T * RH + 9.41695f * 10 - 3 * T * T + 7.28898f * 10 - 3 * RH * RH + 3.45372f * 10
				- 4 * T * T * RH - 8.14971f * 10 - 4 * T * RH * RH + 1.02102f * 10 - 5 * T * T * RH * RH
				- 3.8646f * 10 - 5 * T * T * T + 2.91583f * 10 - 5 * RH * RH * RH + 1.42721f * 10 - 6 * T * T * T
				* RH + 1.97483f * 10 - 7 * T * RH * RH * RH - 2.18429f * 10 - 8 * T * T * T  * RH * RH + 8.43296f
				* 10 - 10 * T * T * RH * RH * RH - 4.81975f * 10 - 11 * T * T * T * RH * RH * RH;
	}
}
