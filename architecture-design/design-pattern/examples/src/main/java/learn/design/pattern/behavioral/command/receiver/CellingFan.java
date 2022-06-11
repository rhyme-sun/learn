package learn.design.pattern.behavioral.command.receiver;

/**
 * 吊扇
 */
public class CellingFan {

    public static final int HIGH = 3;
    public static final int MEDIUM = 2;
    public static final int LOW = 1;
    public static final int OFF = 0;

    /**
     * 吊扇速度，记录吊扇速度状态
     */
    int speed;

    public CellingFan() {
        speed = OFF;
    }

    public void high() {
        speed = HIGH;
        System.out.println("设置高转速");
    }

    public void medium() {
        speed = MEDIUM;
        System.out.println("设置中转速");
    }

    public void low() {
        speed = LOW;
        System.out.println("设置低转速");
    }

    public void off() {
        speed = OFF;
        System.out.println("关闭吊扇");
    }

    public int getSpeed() {
        return speed;
    }
}
