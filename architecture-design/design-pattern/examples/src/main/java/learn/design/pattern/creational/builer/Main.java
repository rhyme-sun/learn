package learn.design.pattern.creational.builer;

/**
 * 测试类
 */
public class Main {

    public static void main(String[] args) {
        ResourcePoolConfig resourcePoolConfig = new ResourcePoolConfig.Builder()
                .setName("common-thread-pool")
                .setMaxTotal(10)
                .setMaxIdle(5)
                .setMinIdle(1)
                .build();
        System.out.println(resourcePoolConfig);
    }
}
