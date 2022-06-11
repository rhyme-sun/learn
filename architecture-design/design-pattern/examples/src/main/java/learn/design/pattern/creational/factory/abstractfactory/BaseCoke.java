package learn.design.pattern.creational.factory.abstractfactory;

/**
 * 可乐抽象类
 */
public abstract  class BaseCoke {

    /**
     * 可乐名称
     */
    String name;

    /**
     * 准备可乐
     */
    protected abstract void prepare();
}
