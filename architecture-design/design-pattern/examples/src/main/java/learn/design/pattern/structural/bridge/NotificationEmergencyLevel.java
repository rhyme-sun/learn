package learn.design.pattern.structural.bridge;

/**
 * API 接口监控告警等级
 */
public enum NotificationEmergencyLevel {

    /**
     * 严重
     */
    SEVERE,

    /**
     * 紧急
     */
    URGENCY,

    /**
     * 一般
     */
    NORMAL,

    /**
     * 不重要
     */
    TRIVIAL
}
