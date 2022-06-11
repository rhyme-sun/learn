package learn.design.pattern.creational.factory.simplefactory;

/**
 * 测试类
 */
public class Main {

    public static void main(String[] args) {
        BasePizza beef = PizzaStore.createPizza(BasePizza.PizzaType.DURIAN_PIZZA);
        BasePizza durian = PizzaStore.createPizza(BasePizza.PizzaType.BEEF_PIZZA);
    }
}
