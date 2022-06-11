package learn.design.pattern.behavioral.observer.sysn;

/**
 * 观察者接口
 */
public interface Observer {

	/**
	 * 更新消息
	 */
	void update(float temp, float humidity, float pressure);
}
