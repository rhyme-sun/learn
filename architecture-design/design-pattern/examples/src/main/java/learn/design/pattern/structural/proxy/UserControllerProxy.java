package learn.design.pattern.structural.proxy;

/**
 * 用户服务代理类，通过继承原始业务类并调用父类方法执行业务逻辑，并使用 {@link MetricsCollector 指标收集器}来收集 api 请求数据。
 * 除了继承的方式调用业务方法外，还可以通过和业务类实现同一个接口的方式来进行代理。
 * 不过，这样代码实现还是有点问题：
 * 一方面，我们需要在代理类中，将原始类中的所有的方法，都重新实现一遍，并且为每个方法都附加相似的代码逻辑。
 * 另一方面，如果要添加的附加功能的类有不止一个，我们需要针对每个类都创建一个代理类。
 *
 * 解决办法：动态代理，所谓动态代理（Dynamic Proxy），就是我们不事先为每个原始类编写代理类，而是在运行的时候，动态地创建原始类对应的
 * 代理类，然后在系统中用代理类替换掉原始类。
 */
public class UserControllerProxy extends UserController {

    // 依赖注入
    private MetricsCollector metricsCollector;

    public Object login(String telephone, String password) {
        long startTimestamp = System.currentTimeMillis();

        // 代理原本类的登录功能
        Object userVo = super.login(telephone, password);

        long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimestamp;
        RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimestamp);
        metricsCollector.recordRequest(requestInfo);

        return userVo;
    }

    public Object register(String telephone, String password) {
        long startTimestamp = System.currentTimeMillis();

        // 代理原本类的注册功能
        Object userVo = super.register(telephone, password);

        long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimestamp;
        RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
        metricsCollector.recordRequest(requestInfo);

        return userVo;
    }
}

