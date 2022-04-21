package learn.algorithm.recursion;

import java.util.Stack;

/**
 * 汉诺塔问题
 */
public class HanoiTower {

    /**
     * 方法 1，最容易理解的方法
     */
    static void hanoi1(int n) {
        // 要求将盘子从左边移动到右边
        leftToRight(n);
    }

    private static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("将盘子 " + n + " 从左边移动到右边");
            return;
        }
        leftToMid(n - 1);
        System.out.println("将盘子 " + n + " 从左边移动到右边");
        midToRight(n - 1);
    }

    private static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("将盘子 " + n + " 从左边移动到中间");
            return;
        }
        leftToRight(n - 1);
        System.out.println("将盘子 " + n + " 从左边移动到中间");
        rightToMid(n - 1);
    }

    private static void midToRight(int n) {
        if (n == 1) {
            System.out.println("将盘子 " + n + " 从中间移动到右边");
            return;
        }
        midToLeft(n - 1);
        System.out.println("将盘子 " + n + " 从中间移动到右边");
        leftToRight(n - 1);
    }

    private static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("将盘子 " + n + " 从右边移动到中间");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("将盘子 " + n + " 从右边移动到中间");
        leftToMid(n - 1);
    }

    private static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("将盘子 " + n + " 从中间移动到左边");
            return;
        }
        midToRight(n - 1);
        System.out.println("将盘子 " + n + " 从中间移动到左边");
        rightToLeft(n - 1);
    }

    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("将盘子 " + n + " 从右边移动到左边");
            return;
        }
        rightToMid(n - 1);
        System.out.println("将盘子 " + n + " 从右边移动到左边");
        midToLeft(n - 1);
    }

    /**
     * 方法 2，方法 1 的进一步抽象，通过增加递归函数的参数增加递归函数的可能性，以支持更多的功能。
     */
    static void hanoi2(int n) {
        // 要求将盘子从左边移动到右边
        move(n, "左边", "右边", "中间");
    }

    private static void move(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("将盘子 " + n + " 从" + from + "移动到" + to);
            return;
        }
        move(n - 1, from, other, to);
        System.out.println("将盘子 " + n + " 从" + from + "移动到" + to);
        move(n - 1, other, to, from);
    }

    /**
     * 非递归版本
     */
    static void hanoi3(int n) {
        if (n < 1) {
            return;
        }
        Stack<Record> stack = new Stack<>();
        stack.add(new Record(false, n, "左边", "右边", "中间"));
        while (!stack.isEmpty()) {
            Record cur = stack.pop();
            if (cur.base == 1) {
                System.out.println("将盘子 " + cur.base + " 从" + cur.from + "移动到" + cur.to);
                if (!stack.isEmpty()) {
                    stack.peek().finish = true;
                }
            } else {
                if (!cur.finish) {
                    stack.push(cur);
                    stack.push(new Record(false, cur.base - 1, cur.from, cur.other, cur.to));
                } else {
                    System.out.println("将盘子 " + cur.base + " 从" + cur.from + "移动到" + cur.to);
                    stack.push(new Record(false, cur.base - 1, cur.other, cur.to, cur.from));
                }
            }
        }
    }

    static class Record {
        boolean finish;
        int base;
        String from;
        String to;
        String other;

        public Record(boolean f1, int b, String f, String t, String o) {
            finish = false;
            base = b;
            from = f;
            to = t;
            other = o;
        }
    }

    public static void main(String[] args) {
        // 盘子起始在最左边，要求全部移动到右边
        hanoi1(3);
        System.out.println();
        hanoi2(3);
        System.out.println();
        hanoi3(3);
    }
}
