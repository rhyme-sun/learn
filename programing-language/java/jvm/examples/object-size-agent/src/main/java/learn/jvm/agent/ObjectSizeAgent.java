package learn.jvm.agent;

import java.lang.instrument.Instrumentation;

/**
 * ObjectSizeAgent 获取对象大小
 */
public class ObjectSizeAgent {

    private static Instrumentation inst;

    /**
     * jvm 参数形式启动，运行此方法
     *
     * @param agentArgs agentArgs
     * @param _inst     inst
     */
    public static void premain(String agentArgs, Instrumentation _inst) {
        inst = _inst;
    }

    /**
     * 动态 attach 方式启动，运行此方法
     *
     * @param agentArgs agentArgs
     * @param _inst     inst
     */
    public static void agentmain(String agentArgs, Instrumentation _inst) {
        inst = _inst;
    }

    public static long sizeOf(Object o) {
        return inst.getObjectSize(o);
    }
}
