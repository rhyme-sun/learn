package learn.spring.aop.xml.service;

/**
 * MyCalculator.
 */
public class MyCalculator /*implements Calculator*/ {

    public Integer add(Integer i, Integer j) {
        Integer result = i + j;
        // 方法内部平级调用，
        show(result);
        return result;
    }

    public Integer sub(Integer i, Integer j) {
        Integer result = i - j;
        return result;
    }

    public Integer mul(Integer i, Integer j) {
        Integer result = i * j;
        return result;
    }

    public Integer div(Integer i, Integer j) {
        Integer result = i / j;
        return result;
    }

    public Integer show(Integer i) {
        System.out.println("show .....");
        System.out.println(this.getClass());
        return i;
    }
}
