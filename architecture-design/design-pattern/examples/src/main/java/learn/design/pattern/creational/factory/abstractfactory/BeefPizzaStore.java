package learn.design.pattern.creational.factory.abstractfactory;

/**
 * 牛肉披萨商店
 */
public class BeefPizzaStore implements IPizzaStore {

    @Override
    public BasePizza createPizza() {
        // 省略牛肉创建复杂工艺
        return new BeefPizza();
    }

    /**
     * 创建可乐
     *
     * @return 可乐
     */
    @Override
    public BaseCoke createCoke() {
        // 创建百事可乐
        return new Pepsi();
    }
}
