package learn.design.pattern.structural.proxy;

import java.util.Objects;

/**
 * 指标收集器，用来收集接口请求的原始数据，比如访问时间、处理时长等。
 */
public class MetricsCollector {

    /**
     * 请求信息存储类
     */
    private MetricsStorage metricsStorage ;

    public MetricsCollector(MetricsStorage metricsStorage) {
        this.metricsStorage = metricsStorage;
    }

    /**
     * 收集并存储请求信息
     *
     * @param requestInfo 请求信息
     */
    public void recordRequest(RequestInfo requestInfo) {
        if (requestInfo == null || Objects.equals("", requestInfo.getApiName())) {
            return;
        }
        System.out.println(requestInfo.getApiName());
        System.out.println(requestInfo.getTimestamp());
        System.out.println(requestInfo.getResponseTime());
        //metricsStorage.saveRequestInfo(requestInfo);
    }
}
