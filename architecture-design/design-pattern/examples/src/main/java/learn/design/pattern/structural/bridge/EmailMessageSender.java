package learn.design.pattern.structural.bridge;

/**
 * 邮件消息发送器
 */
public class EmailMessageSender implements MessageSender {

    @Override
    public boolean sendMessage(String message) {
        return false;
    }
}
