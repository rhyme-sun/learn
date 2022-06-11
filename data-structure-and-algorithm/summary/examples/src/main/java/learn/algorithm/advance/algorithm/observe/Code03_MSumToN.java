package learn.algorithm.advance.algorithm.observe;

/**
 * 对数据找规律
 * 题目描述如下：
 * 定义一种数：可以表示成若干（数量 > 1）连续正数和的数。
 * 比如：5 = 2+3，5 就是这样的数；12 = 3+4+5，12就是这样的数。给定一个参数 N，返回是不是可以表示成若干连续正数和的数。
 */
public class Code03_MSumToN {

    public static boolean isMSum1(int num) {
        for (int start = 1; start <= num; start++) {
            int sum = start;
            for (int j = start + 1; j <= num; j++) {
                if (sum + j > num) {
                    break;
                }
                if (sum + j == num) {
                    return true;
                }
                sum += j;
            }
        }
        return false;
    }

    static boolean isMSum2(int num) {
        // 判断某个数是否为 2 的幂（二进制只有一个位置有 1）
        // 二进制取出最右侧的 1 和本身相等则说明是 2 的幂
//        return num == (num & (~num + 1));
//        return num == (num & (-num));
        return (num & (num - 1)) != 0;
    }

    public static void main(String[] args) {
        for (int num = 1; num < 200; num++) {
            System.out.println(num + " : " + isMSum1(num));
        }
        System.out.println("test begin");
        for (int num = 1; num < 5000; num++) {
            if (isMSum1(num) != isMSum2(num)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }
}
