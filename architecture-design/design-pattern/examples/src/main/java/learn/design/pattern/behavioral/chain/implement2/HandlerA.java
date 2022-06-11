package learn.design.pattern.behavioral.chain.implement2;

/**
 * 处理器 A
 */
public class HandlerA implements IHandler {

    @Override
    public boolean handle() {
        // 判断能否处理，能处理进行处理并返回true，不能处理返回false
        System.out.println("HandlerA doHandle ...");
        return true;
    }
}
