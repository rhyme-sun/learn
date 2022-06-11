package learn.design.pattern.behavioral.observer.asysn;

import java.util.concurrent.Executor;

/**
 * 异步事件总线
 */
public class AsyncEventBus extends EventBus {

    public AsyncEventBus(Executor executor) {
        super(executor);
    }
}