package learn.java.jvm.oom;

import java.util.HashSet;
import java.util.Set;

/**
 * RuntimeConstantPoolOOM.
 * JDK 6: -XX:PermSize=6M -XX:MaxPermSize=6M
 * JDK 7: -XX:PermSize=6M -XX:MaxPermSize=6M -Xmx6M
 * JDK 8: -XX:MaxMetaspaceSize=10M -Xmx6M
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
