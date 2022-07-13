package learn.java.jvm.gc;

/**
 * PretenureSizeThresholdExample.
 * <p>
 *
 *
 */
public class PretenureSizeThresholdExample {

    // 大对象进入老年代
    // -client -verbose:gc -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
    public static void main(String[] args) {
        byte[] allocation = new byte[4* 1024 * 1024];
    }
}
