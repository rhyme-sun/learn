package learn.java.jvm.executive.methodcall;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * MethodHandleExample.
 */
public class MethodHandleExample {

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new Foo();
        getPrintlnMethodHandle(obj).invokeExact("simon");
    }

    // 模拟 invokevirtual 指令的执行过程，根据 receiver 的实际类型在虚方法表里找到对应方法，返回方法句柄
    private static MethodHandle getPrintlnMethodHandle(Object receiver) throws Throwable {
        // MethodType 表明了方法类型，void.class 说明了返回类型为 void， String.class 说明了方法参数类型为 String
        MethodType mt = MethodType.methodType(void.class, String.class);
        // 在指定类中查找符合给定的方法名称、方法类型，并且符合调用权限的虚方法句柄
        return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
    }

    static class Foo {
        public void println(String s) {
            System.out.println(s);
        }
    }
}
