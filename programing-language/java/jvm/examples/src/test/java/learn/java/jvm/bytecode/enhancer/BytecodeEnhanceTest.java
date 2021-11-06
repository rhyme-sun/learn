package learn.java.jvm.bytecode.enhancer;

import learn.java.jvm.bytecode.enhancer.asm.Generator;
import learn.java.jvm.bytecode.enhancer.bytebuddy.ByteBuddyEnhancer;
import learn.java.jvm.bytecode.enhancer.cglib.CglibEnhancer;
import learn.java.jvm.bytecode.enhancer.javassist.JavassistEnhancer;
import learn.java.jvm.bytecode.enhancer.jdk.JdkEnhancer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for bytecode.
 */
//@Slf4j
public class BytecodeEnhanceTest {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    @Test
    public void testEnhanceWithAsm() {
        // 执行前需执行需重新 build 工程并执行 Generator 织入增强字节码
        log.info("After enhance");
        Base base = new Base();
        base.process();
    }

    @Test
    public void testEnhanceWithJavassist() throws Exception {
        // 执行前需执行需重新 build 工程
        log.info("After enhance");
        Base enhancedBase = JavassistEnhancer.createEnhancedBase();
        enhancedBase.process();
    }

    @Test
    public void testEnhanceWithAspectJ() {
        // 执行前需要重新编译
        Base base = new Base();
        log.info("After enhance");
        base.process();
    }

    @Test
    public void testEnhanceWithByteBuddy() throws Exception {
        log.info("After enhance");
        Base enhancedBase = ByteBuddyEnhancer.createEnhancedBase();
        enhancedBase.process();
    }

    @Test
    public void testEnhanceWithCglib() {
        log.info("After enhance");
        Base enhancedBase = CglibEnhancer.createEnhancedBase();
        enhancedBase.process();
    }

    @Test
    public void testEnhanceWithJdk() {
        log.info("After enhance");
        IBase enhancedBase = JdkEnhancer.createEnhancedBase();
        enhancedBase.process();
    }
}
