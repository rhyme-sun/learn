package learn.design.pattern.structural.bridge;

/**
 * for test.
 */
public class Main {

    public static void main(String[] args) {
        // 实现类层次
        MessageSender messageSender = new EmailMessageSender();
        // 抽象类层次
        Notification notification = new SevereNotification(messageSender);

        // 抽象类扩展，涵盖了抽象层次的功能，并进行增强
        EmailAlertHandler handler = new EmailAlertHandler(notification);
        handler.check(new ApiStatInfo());
    }
}
