package learn.design.pattern.behavioral.chain.log;

/**
 * StdoutLogger
 */
public class StdoutLogger extends Logger {

    public StdoutLogger(int mask) {
        this.mask = mask;
    }

    @Override
    protected void writeMessage(String msg) {
        System.out.println("Writing to stdout: " + msg);
    }
}
