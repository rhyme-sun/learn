package learn.design.pattern.behavioral.chain.implement1;

/**
 * 处理器 B
 *
 */
public class HandlerB extends Handler {

    @Override
    protected boolean doHandle() {
        int random = (int) (Math.random() * 10 / 1);
        boolean canHandle = random % 2 == 0;
        System.out.println("HanderB can handle: " + random);
        if (canHandle) {
            System.out.println("HandlerB doHandle ...");
            return true;
        }
        return false;
    }
}
