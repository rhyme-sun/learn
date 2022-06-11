package learn.design.pattern.behavioral.observer.sysn;

/**
 * 观察者模式主题（被观察者）。
 */
public interface Subject {

	/**
	 * 观察者注册
	 */
	void registerObserver(Observer observer);

	/**
	 * 取消注册
	 */
	void removeObserver(Observer observer);

	/**
	 * 当主题状态改变时，这个方法会被调用，以通知所有的观察者。
	 */
	void notifyObservers();
}
