package learn.design.pattern.creational.factory.simplefactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 披萨店，
 * 简单工厂：对象的创建逻辑简单，将不同子类对象的实现逻辑放到一个方法中，通过 if-else 或者哈希表的方式区分。
 */
public class PizzaStore {

    private static final Map<String, BasePizza> pizzaMap = new HashMap<>();

    static {
        pizzaMap.put(BasePizza.PizzaType.BEEF_PIZZA, new BeefPizza());
        pizzaMap.put(BasePizza.PizzaType.DURIAN_PIZZA, new DurianPizza());
    }

    /**
     * 根据披萨类型创建披萨（map）
     *
     * @param type 披萨类型
     * @return 披萨
     * @throws UnsupportedOperationException 无对应类型披萨
     */
    public static BasePizza createPizza2(String type) {
        BasePizza pizza = pizzaMap.get(type);
        if (pizza == null) {
            throw new UnsupportedOperationException("敬请期待...");
        }
        return pizza;
    }

    /**
     * 根据披萨类型创建披萨（if-else）
     *
     * @param type 披萨类型
     * @return 披萨
     * @throws UnsupportedOperationException 无对应类型披萨
     */
    public static BasePizza createPizza(String type) {
        if (Objects.equals(BasePizza.PizzaType.BEEF_PIZZA, type)) {
            return new BeefPizza();
        } else if (Objects.equals(BasePizza.PizzaType.DURIAN_PIZZA, type)) {
            return new DurianPizza();
        } else {
            throw new UnsupportedOperationException("敬请期待...");
        }
    }
}
