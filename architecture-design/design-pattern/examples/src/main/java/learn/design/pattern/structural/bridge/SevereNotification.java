package learn.design.pattern.structural.bridge;

/**
 * 严重信息通知，通过构造器注入不同的消息发送实现类，将告警消息通过不同的方式发送出去
 */
public class SevereNotification extends Notification {

    public SevereNotification(MessageSender messageSender) {
        super(messageSender);
    }

    @Override
    void inform(String message) {
        // 严重消息通知
        this.messageSender.sendMessage(message);
    }
}
