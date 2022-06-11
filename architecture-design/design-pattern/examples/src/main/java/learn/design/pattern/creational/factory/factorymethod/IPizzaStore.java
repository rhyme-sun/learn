package learn.design.pattern.creational.factory.factorymethod;

/**
 * 工厂方法：每个对象的创建不在集中到一个类中实现，而是通过实现创建对象接口而将不同对象的创建逻辑分散到不同的实现类中。
 * 披萨商店接口类，由于工艺进步，每个披萨创建变得复杂，将不同类型的披萨都在一个披萨商店中创建就会过于臃肿，也不方便扩展，所以这里设计披萨商店
 * 接口类，每种类型的披萨有单独的披萨商店实现类来创建
 */
public interface IPizzaStore {

    BasePizza createPizza();
}
