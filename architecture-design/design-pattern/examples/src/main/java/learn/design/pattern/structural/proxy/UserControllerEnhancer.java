package learn.design.pattern.structural.proxy;

/**
 * 用户相关服务，使用 {@link MetricsCollector 指标收集器}来收集 api 请求数据。
 * 很明显，上面的写法有两个问题：
 * 第一，性能计数器框架代码侵入到业务代码中，跟业务代码高度耦合。如果未来需要替换这个框架，那替换的成本会比较大。
 * 第二，收集接口请求的代码跟业务代码无关，本就不应该放到一个类中。业务类最好职责更加单一，只聚焦业务处理。
 *
 * 解决办法，为了将业务代码和指标统计框架代码解耦，代理模式就派上用场了。
 * @see UserControllerProxy 用户服务代理类
 */
public class UserControllerEnhancer {

    // 依赖注入
    private MetricsCollector metricsCollector;

    public Object login(String telephone, String password) {
        long startTimestamp = System.currentTimeMillis();

        // 登录逻辑...

        long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimestamp;
        RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimestamp);
        metricsCollector.recordRequest(requestInfo);

        return null;
    }

    public Object register(String telephone, String password) {
        long startTimestamp = System.currentTimeMillis();

        // 注册逻辑

        long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimestamp;
        RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
        metricsCollector.recordRequest(requestInfo);

        return null;
    }
}

