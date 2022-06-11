package learn.design.pattern.creational.factory.abstractfactory;

/**
 * 无敌牛肉披萨
 */
public class BeefPizza extends BasePizza {

    public BeefPizza() {
        this.name = "无敌牛肉披萨";
        this.ingredients.add("厚面饼");
        this.ingredients.add("芝士");
        this.ingredients.add("牛肉");
        progress();
    }

    @Override
    public void prepare() {
        System.out.println("开始准备做：" + name);
        System.out.println("准备原料：");
        this.ingredients.forEach(System.out::println);
    }
}
