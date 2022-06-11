package learn.design.pattern.creational.factory.factorymethod;

/**
 * 牛肉披萨商店
 */
public class BeefPizzaStore implements IPizzaStore {

    @Override
    public BasePizza createPizza() {
        // ...
        return new BeefPizza();
    }
}
