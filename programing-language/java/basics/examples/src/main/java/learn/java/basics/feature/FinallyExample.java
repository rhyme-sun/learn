package learn.java.basics.feature;

/**
 * FinallyExample.
 * try 中有 return, 会先将值暂存，无论 finally 语句中对该值做什么处理，最终返回的都是 try 语句中的暂存值；
 * try 与 finally 语句中均有 return 语句，会忽略 try 中 return。
 */
public class FinallyExample {

    public static void main(String[] args) {
        System.out.println(run1()); // 0
        System.out.println(run2()); // 1
    }

    private static int run1() {
         int x = 0;
        try {
            return x;
        } finally {
            x++;
        }
    }

    private static int run2() {
        int x = 0;
        try {
            return x;
        } finally {
            x++;
            return x;
        }
    }
}
