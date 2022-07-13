package learn.spring.ioc.instantiation.factorymethod;

/**
 * BeanByFactoryMethod.
 */
public class BeanByFactoryMethod {

    // 工厂方法可以其他类声明
    public static BeanByFactoryMethod create() {
        return new BeanByFactoryMethod();
    }
}
