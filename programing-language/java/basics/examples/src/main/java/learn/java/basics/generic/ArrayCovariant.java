package learn.java.basics.generic;

/**
 * 数组协变
 */
public class ArrayCovariant {

    public static void main(String[] args) {
        Apple[] apples = new Apple[10];
        Fruit[] fruits = apples;
        fruits[0] = new Apple();
        fruits[1] = new GreenApple();

        try {
            // ArrayStoreException
            fruits[3] = new Fruit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // ArrayStoreException
            fruits[4] = new Orange();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
