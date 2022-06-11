package learn.design.pattern.structural.proxy;

/**
 * for test.
 */
public class Main {

	public static void main(String[] args) {
		//MetricsCollectorProxy使用举例
		MetricsCollectorProxy proxy = new MetricsCollectorProxy();
		IUserController userController = (IUserController) proxy.createProxy(new UserController());
		userController.login("", "");
	}
}
