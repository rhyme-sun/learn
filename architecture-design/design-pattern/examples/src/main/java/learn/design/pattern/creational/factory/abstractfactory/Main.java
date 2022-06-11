package learn.design.pattern.creational.factory.abstractfactory;

/**
 * 测试类
 */
public class Main {

    public static void main(String[] args) {
        IPizzaStore beefPizzaStore = PizzaStoreFactory.createPizzaStore(PizzaStoreFactory.StoreType.BEEF_PIZZA_STORE);
        BasePizza beefPizza = beefPizzaStore.createPizza();
        BaseCoke pepsi = beefPizzaStore.createCoke();

        IPizzaStore durianPizzaStore = PizzaStoreFactory.createPizzaStore(PizzaStoreFactory.StoreType.DURIAN_PIZZA_STORE);
        BasePizza durianPizza = durianPizzaStore.createPizza();
        BaseCoke cocaCola = durianPizzaStore.createCoke();
    }
}
