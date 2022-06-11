package learn.design.pattern.structural.bridge;

import java.util.List;
import java.util.Objects;

/**
 * Api 消息处理类，并发送处理的消息通过邮件发送
 */
public class EmailAlertHandler implements AlertHandler {

    private Notification notification;

    /**
     * 邮件地址
     */
    private List<String> emailAddresses;

    public EmailAlertHandler(Notification notification) {
        this.notification = Objects.requireNonNull(notification,"Notification can not be null");
    }

    @Override
    public void check(ApiStatInfo apiStatInfo) {
        boolean needAlert = false;
        if (needAlert) {
            notification.inform("");
        }
    }
}
