package learn.java.jvm.gc;

/**
 * TenuringThresholdExample.
 */
public class TenuringThresholdExample {

    private static final int _1MB = 1024 * 1024;

    // 对象晋升
    // -client -verbose:gc -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -Xlog:gc* -Xlog:gc+age=trace -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }
}
