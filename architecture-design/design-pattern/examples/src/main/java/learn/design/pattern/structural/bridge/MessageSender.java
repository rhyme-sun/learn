package learn.design.pattern.structural.bridge;

/**
 * 消息发送接口类
 */
public interface MessageSender {

    /**
     * 发送消息
     *
     * @param message 消息内容
     * @return true/false 发送成功/失败
     */
    boolean sendMessage(String message);
}
