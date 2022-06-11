package learn.design.pattern.behavioral.chain.implement1;

/**
 * 测试
 */
public class Main {

    public static void main(String[] args) {
        HandlerChain chain = new HandlerChain();
        chain.addHandler(new HandlerA());
        chain.addHandler(new HandlerB());
        chain.handle();
    }
}
