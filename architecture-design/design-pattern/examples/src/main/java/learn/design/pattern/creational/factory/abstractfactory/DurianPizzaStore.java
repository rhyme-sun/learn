package learn.design.pattern.creational.factory.abstractfactory;

/**
 * 榴莲披萨工厂
 *
 * @author ykthree
 * @date 2020/5/27 21:50
 */
public class DurianPizzaStore implements IPizzaStore {

    @Override
    public BasePizza createPizza() {
        // 省略榴莲披萨的创建复杂工艺
        return new DurianPizza();
    }

    /**
     * 创建可乐
     *
     * @return 可乐
     */
    @Override
    public BaseCoke createCoke() {
        // 创建可口可乐
        return new CocaCola();
    }
}
