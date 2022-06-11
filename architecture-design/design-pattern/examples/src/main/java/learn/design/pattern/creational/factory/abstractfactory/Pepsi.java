package learn.design.pattern.creational.factory.abstractfactory;

/**
 * 百事可乐
 */
public class Pepsi extends BaseCoke {

    public Pepsi() {
        this.name = "百事可乐";
        prepare();
    }

    /**
     * 准备可乐
     */
    @Override
    public void prepare() {
        System.out.println("开始准备：" + name);
    }
}
