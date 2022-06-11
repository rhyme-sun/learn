package learn.design.pattern.structural.proxy;

import java.util.List;
import java.util.Map;

/**
 * 指标存储接口，设计成接口的原因是因为存储的方法可能有多种，比如用Redis、HBase等方式来存储，设计成接口方便扩展
 * 新的指标存储方法（面向接口而非实现编程）
 */
public interface MetricsStorage {

    /**
     * 保存请求信息
     *
     * @param requestInfo 请求信息
     */
    void saveRequestInfo(RequestInfo requestInfo);

    /**
     * 获取某个api在指定时间范围内的请求信息数据
     *
     * @param apiName           api名称
     * @param startTimeInMillis 起始时间
     * @param endTimeInMillis   终止时间
     * @return 请求信息数据列表
     */
    List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis);

    /**
     * 获取所有api在指定时间范围内的请求信息数据
     *
     * @param startTimeInMillis 起始时间
     * @param endTimeInMillis   终止时间
     * @return 请求信息数据，Map的key为api名称，value为请求信息列表
     */
    Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis);
}
