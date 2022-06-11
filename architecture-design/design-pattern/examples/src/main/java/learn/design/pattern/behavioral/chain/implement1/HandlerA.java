package learn.design.pattern.behavioral.chain.implement1;

/**
 * 处理器A
 */
public class HandlerA extends Handler {

    @Override
    protected boolean doHandle() {
        int random = (int) (Math.random() * 10 / 1);
        boolean canHandle = random % 2 == 0;
        System.out.println("HanderA can handle: " + random);
        // 判断能处理，能处理就处理，并将处理状态置为true
        if (canHandle) {
            System.out.println("HandlerA doHandle ...");
            return true;
        }
        return false;
    }
}
