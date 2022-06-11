package learn.design.pattern.behavioral.chain.implement2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 基于数组的处理链
 */
public class HandlerChain {

    private List<IHandler> handlers = new ArrayList<>();

    public void addHandler(IHandler handler) {
        handlers.add(Objects.requireNonNull(handler));
    }

    public void handle() {
        for (IHandler handler : handlers) {
            boolean handled = handler.handle();
            if (handled) {
                break;
            }
        }
    }
}
