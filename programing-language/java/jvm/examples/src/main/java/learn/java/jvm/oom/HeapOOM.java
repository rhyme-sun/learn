package learn.java.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * HeapOOM.
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {

    static class ObjectOOM {}

    public static void main(String[] args) {
        List<ObjectOOM> list = new ArrayList<>();
        while (true) {
            list.add(new ObjectOOM());
        }
    }
}
