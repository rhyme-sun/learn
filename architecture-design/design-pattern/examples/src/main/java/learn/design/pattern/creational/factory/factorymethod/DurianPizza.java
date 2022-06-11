package learn.design.pattern.creational.factory.factorymethod;

/**
 * 至尊榴莲披萨
 */
public class DurianPizza extends BasePizza {

    public DurianPizza() {
        this.name = "至尊榴莲披萨";
        this.ingredients.add("薄面饼");
        this.ingredients.add("芝士");
        this.ingredients.add("榴莲");
        progress();
    }

    @Override
    public void prepare() {
        System.out.println("开始准备做：" + name);
        System.out.println("准备原料：");
        this.ingredients.forEach(System.out::println);
    }
}
