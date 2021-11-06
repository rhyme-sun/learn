package learn.java.jvm.bytecode.enhancer;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base，期望通过字节码增强技术打印 {@link this#doSomething()} 方法执行时间
 */
public class Base {

    private static final Logger log = LoggerFactory.getLogger(Base.class);

    public void process() {
        doSomething();
    }

    private void doSomething() {
        try {
            log.info("process ...");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("Interrupted ", e);
        }
    }
}
