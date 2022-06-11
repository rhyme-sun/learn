package learn.design.pattern.behavioral.chain.log;

/**
 * 日志父级抽象类，注意，该例子不是日志类的推荐实现方式
 */
public abstract class Logger {

    public static final int ERR = 3;
    public static final int NOTICE = 5;
    public static final int DEBUG = 7;
    protected int mask;
    /**
     * The next element in the chain of responsibility
     */
    protected Logger next;

    public Logger setNext(Logger l) {
        next = l;
        return this;
    }

    public final void message(String msg, int priority) {
        if (priority <= mask) {
            writeMessage(msg);
            if (next != null) {
                next.message(msg, priority);
            }
        }
    }

    protected abstract void writeMessage(String msg);
}
