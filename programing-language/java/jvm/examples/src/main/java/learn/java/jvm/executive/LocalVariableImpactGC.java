package learn.java.jvm.executive;

/**
 * LocalVariableImpactGC.
 * 局部变量表可重用对 GC 的影响。
 * -verbose:gc
 */
public class LocalVariableImpactGC {

    public static void main(String[] args) {
//        run1();
//        run2();
//        run3();
        run4();
    }

    // GC 和局部变量在同一个作用域，bytes 是 GC Roots，数组对象不会被回收
    private static void run1() {
        byte[] bytes = new byte[64 * 1024 * 1024];
        System.gc();
    }

    // GC 和局部变量在同一个作用域，bytes 置为 null，数组对象被回收
    private static void run2() {
        byte[] bytes = new byte[64 * 1024 * 1024];
        bytes = null;
        System.gc();
    }

    // GC 和局部变量不在同一个作用域，bytes 虽然已经不再使用了，但还是不会被回收
    private static void run3() {
        {
            byte[] bytes = new byte[64 * 1024 * 1024];
        }
        System.gc();
    }

    // GC 和局部变量不在同一个作用域，变量槽被作用域外的另一个变量覆盖，bytes 会被回收
    private static void run4() {
        {
            byte[] bytes = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }
}
