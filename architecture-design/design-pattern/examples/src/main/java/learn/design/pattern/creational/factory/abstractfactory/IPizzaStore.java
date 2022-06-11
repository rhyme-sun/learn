package learn.design.pattern.creational.factory.abstractfactory;

/**
 * 抽象工厂：提供一个接口，用于创建相关或依赖对象的家族
 *
 * 披萨商店接口类，由于用户需求，披萨商店不光创建披萨，还同时生产对应可乐，这时候接口同时提供创建披萨和可乐的方法
 */
public interface IPizzaStore {

    BasePizza createPizza();

    BaseCoke createCoke();
}
