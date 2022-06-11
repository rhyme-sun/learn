package learn.design.pattern.creational.factory.abstractfactory;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 披萨商店工厂（工厂的工厂）
 */
public class PizzaStoreFactory {

    private static final Map<String, IPizzaStore> pizzaStoreMap = new HashMap<>();

    static {
        pizzaStoreMap.put(StoreType.BEEF_PIZZA_STORE, new BeefPizzaStore());
        pizzaStoreMap.put(StoreType.DURIAN_PIZZA_STORE, new DurianPizzaStore());
    }

    /**
     * 创建披萨商店
     *
     * @param type 披萨商店类型
     * @return 披萨商店
     * @throws UnsupportedOperationException 没有对应类型的披萨商店
     */
    public static IPizzaStore createPizzaStore2(String type) {
        IPizzaStore pizzaStore = pizzaStoreMap.get(type);
        if (pizzaStore == null) {
            throw new UnsupportedOperationException("敬请期待");
        }
        return pizzaStore;
    }


    /**
     * 创建披萨商店
     *
     * @param type 披萨商店类型
     * @return 披萨商
     * @throws UnsupportedOperationException 没有对应类型的披萨商店
     */
    public static IPizzaStore createPizzaStore(String type) {
        if (Objects.equals(StoreType.BEEF_PIZZA_STORE, type)) {
            return new BeefPizzaStore();
        } else  if (Objects.equals(StoreType.DURIAN_PIZZA_STORE, type)) {
            return new DurianPizzaStore();
        } else {
            throw new UnsupportedOperationException("敬请期待");
        }
    }


    /**
     * 披萨类
     */
    public static class StoreType {

        /**
         * 无敌牛肉披萨
         */
        public static final String BEEF_PIZZA_STORE = "beef";
        /**
         * 榴莲披萨
         */
        public static final String DURIAN_PIZZA_STORE = "durian";
    }
}
