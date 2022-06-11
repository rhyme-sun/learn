package learn.design.pattern.creational.factory.abstractfactory;

/**
 * 可口可乐
 */
public class CocaCola extends BaseCoke {

    public CocaCola() {
        this.name = "可口可乐";
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
