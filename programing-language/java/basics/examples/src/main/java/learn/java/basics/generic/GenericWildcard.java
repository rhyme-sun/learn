package learn.java.basics.generic;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 泛型通配符
 */
@Slf4j
public class GenericWildcard {

    public static void main(String[] args) {
        extendWildcard();
        superWildcard();
    }

    /**
     * 上边界泛型通配符
     */
    private static void extendWildcard() {
        List<Apple> apples = new ArrayList<>();
        Apple apple = new Apple();
        apples.add(apple);
        // 至少知道 apples 列表中的元素都是 Fruit 的子类，Fruit 是元素的上边界
        List<? extends Fruit> fruits = apples;
        fruits.add(null);
        // 以下编译均不能通过
        //fruits.add(new Apple());
        //fruits.add(new GreenApple());
        //fruits.add(new Fruit());
        //fruits.add(new Orange());
        //fruits.add(new Object());

        // 可以设置 null 值
        fruits.add(null);
        log.info("{}", fruits.get(0));
        fruits.remove(apple);
        log.info("{}", fruits.get(0));
        List<Fruit> fruits2 = new ArrayList<>();
        fruits2.add(new Apple());
        fruits2.add(new GreenApple());
        fruits2.add(new Fruit());
        fruits2.add(new Orange());
    }

    /**
     * 下边界泛型通配符
     */
    private static void superWildcard() {
        List<Fruit> fruits = new ArrayList<>();
        List<? super Apple> apples = fruits;
        apples.add(new Apple());
        apples.add(new GreenApple());

        // 读的对象只能用 Object 接收
        Object object = apples.get(0);
        log.info("{}", object);
    }

    static class Fruit {
    }

    static class Apple extends Fruit {
    }

    static class GreenApple extends Apple {
    }

    static class Orange extends Fruit {
    }
}
