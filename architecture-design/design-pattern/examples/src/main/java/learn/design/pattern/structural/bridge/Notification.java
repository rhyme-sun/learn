package learn.design.pattern.structural.bridge;

import java.util.Objects;

/**
 * API 接口监控告警通知接口类，并且将不同的消息通知方式的抽象行为从 Notification 这一类层次中解耦出去
 */
public abstract class Notification {

    /**
     * 消息通知抽象类，包含了不同的通知方式
     */
    protected MessageSender messageSender;

    /**
     * 通知方法
     *
     * @param message 通知消息
     */
    abstract void inform(String message);

    public Notification(MessageSender messageSender) {
        // fail fast
        this.messageSender = Objects.requireNonNull(messageSender, "MessageSender can not be null");
    }
}
