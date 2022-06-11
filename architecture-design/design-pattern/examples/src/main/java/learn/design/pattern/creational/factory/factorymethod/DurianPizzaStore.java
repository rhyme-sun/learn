package learn.design.pattern.creational.factory.factorymethod;

/**
 * 榴莲披萨工厂
 */
public class DurianPizzaStore implements IPizzaStore {

    @Override
    public BasePizza createPizza() {
        // ...
        return new DurianPizza();
    }
}
