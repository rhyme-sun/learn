package learn.design.pattern.behavioral.observer.asysn;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 事件总线。
 */
public class EventBus {

    private Executor executor;

    private ObserverRegistry registry = new ObserverRegistry();

    /*public EventBus() {
        // MoreExecutors.directExecutor() 是 Google Guava 提供的工具类，看似是多线程，实际上是单线程。之所以要这么实现，
        // 主要还是为了跟 AsyncEventBus 统一代码逻辑，做到代码复用。
        this(MoreExecutors.directExecutor());
    }*/

    protected EventBus(Executor executor) {
        this.executor = executor;
    }

    public void register(Object object) {
        registry.register(object);
    }

    public void post(Object event) {
        List<ObserverAction> observerActions = registry.getMatchedObserverActions(event);
        for (ObserverAction observerAction : observerActions) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    observerAction.execute(event);
                }
            });
        }
    }
}