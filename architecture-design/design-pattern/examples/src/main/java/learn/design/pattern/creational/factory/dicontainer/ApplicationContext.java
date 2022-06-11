package learn.design.pattern.creational.factory.dicontainer;

public interface ApplicationContext {

    /**
     * 根据beanId获取对应的bean
     *
     * @param beanId 对象创建表示
     * @return 创建好的对象
     */
    Object getBean(String beanId);
}
