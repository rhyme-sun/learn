package learn.design.pattern.structural.proxy;

/**
 * 请求信息实体类
 */
public class RequestInfo {

    /**
     * 请求api名称
     */
    private String apiName;

    /**
     * 响应时间
     */
    private double responseTime;

    /**
     * 请求时间戳
     */
    private long timestamp;

    public RequestInfo(String apiName, double responseTime, long timestamp) {
        this.apiName = apiName;
        this.responseTime = responseTime;
        this.timestamp = timestamp;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
