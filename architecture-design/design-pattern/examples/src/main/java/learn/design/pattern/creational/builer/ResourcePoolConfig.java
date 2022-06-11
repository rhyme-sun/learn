package learn.design.pattern.creational.builer;

/**
 * 资源池配置类
 * 建造者设计模式：建造者模式通过链式调用灵活给属性赋值，最终提供统一构造入口（ build 方法）创建需要的对象，避免了对象无效状态，
 * 并且可可以在 build 方法中对参数进行校验，这样就收拢了参数的校验逻辑，避免了校验逻辑无处安放的窘境。
 */
public class ResourcePoolConfig {

    private final String name;
    private final int maxTotal;
    private final int maxIdle;
    private final int minIdle;

    private ResourcePoolConfig(Builder builder) {
        this.name = builder.name;
        this.maxTotal = builder.maxTotal;
        this.maxIdle = builder.maxIdle;
        this.minIdle = builder.minIdle;
    }

    public String getName() {
        return name;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    /**
     * 资源配置类构建器
     */
    public static class Builder {
        private String name;
        private int maxTotal;
        private int maxIdle;
        private int minIdle;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
            return this;
        }

        public Builder setMinIdle(int minIdle) {
            this.minIdle = minIdle;
            return this;
        }

        public String getName() {
            return name;
        }

        public int getMaxTotal() {
            return maxTotal;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public ResourcePoolConfig build() {
            // 校验各参数逻辑可以放到 build 方法里统一处理，这样就收拢了参数的校验逻辑，避免了校验逻辑无处安放的窘境
            return new ResourcePoolConfig(this);
        }
    }
}
