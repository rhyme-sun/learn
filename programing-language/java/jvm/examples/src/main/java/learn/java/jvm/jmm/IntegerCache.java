package learn.java.jvm.jmm;

/**
 * {@link Integer#valueOf} 缓存机制，会缓存 -127 ~ 128 中的值，类似的还有 {@link Short#valueOf}，{@link Long#valueOf}
 */
@SuppressWarnings("ALL")
public class IntegerCache {

    public static void main(String[] args) {
        Integer i = 1;
        Integer j = 1;

        // ture 自动包箱使用了 Integer#valueOf 方法，存在缓存机制，-127 ~ 128 之间的值会被缓存，多次会取到同一个对象
        System.out.println(i == j);
        // false new Integer(1) 则会创建一个新的对象
        System.out.println(i == new Integer(1));
    }
}
