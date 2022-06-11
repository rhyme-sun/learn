package learn.design.pattern.behavioral.chain.implement1;

/**
 * 处理器抽象类
 *
 * @author ykthree
 * @date 2020/7/20 21:30
 */
public abstract class Handler {

    protected Handler successor = null;

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public final void handle() {
        boolean handled = doHandle();
        if (!handled && successor != null) {
            successor.handle();
        }
    }

    protected abstract boolean doHandle();
}
