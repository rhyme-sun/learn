package learn.design.pattern.structural.bridge;

/**
 * 告警处理器，用于处理 api 告警信息
 */
public interface AlertHandler {

    /**
     * 处理 api 告警信息
     *
     * @param apiStatInfo api告警信息
     */
    void check(ApiStatInfo apiStatInfo);
}
