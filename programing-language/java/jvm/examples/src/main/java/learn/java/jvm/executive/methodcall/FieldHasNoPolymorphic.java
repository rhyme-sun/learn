package learn.java.jvm.executive.methodcall;

/**
 * FieldHasNoPolymorphic.
 * 只有虚方法，没有虚字段，子类和父类声明相同的字段，子类会覆盖父类。
 */
public class FieldHasNoPolymorphic {

    public static void main(String[] args) {
        Father gay = new Son();
        System.out.println("This gay has $" + gay.money);
    }

    static class Father {
        public int money = 1;
        public Father() {
            money = 2;
            showMeTheMoney();
        }
        public void showMeTheMoney() {
            System.out.println("I am Father, i have $" + money);
        }
    }
    static class Son extends Father {
        public int money = 3;
        public Son() {
            money = 4;
            showMeTheMoney();
        }
        public void showMeTheMoney() {
            System.out.println("I am Son, i have $" + money);
        }
    }
}
