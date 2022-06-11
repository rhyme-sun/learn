package learn.algorithm.basics.algorithm.bit.usage;

/**
 * 使用位运算实现整数四则运算
 */
public class Code01_BitAddMinusMultiDiv {

    // https://leetcode.cn/problems/sum-of-two-integers/
    static int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;
            int add = (a & b) << 1;
            a = sum;
            b = add;
        }
        return sum;
    }

    static int minus(int a, int b) {
        return add(a, negNum(b));
    }

    // 取 n 的相反数
    // -n = ~n + 1
    private static int negNum(int n) {
        return add(~n, 1);
    }

    static int multi(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = add(res, a);
            }
            b >>>= 1;
            a <<= 1;
        }
        return res;
    }

    // a 和 b 需要转成正数，因此 a 和 b 不能为最小值
    static int div(int a, int b) {
        // 都处理成正数，做运算
        int x = isNeg(a) ? negNum(a) : a;
        int y = isNeg(b) ? negNum(b) : b;
        int res = 0;
        for (int i = 30; i >= 0; i = minus(i, 1)) {
            if ((x >> i) >= y) {
                res |= (1 << i);
                x = minus(x, y << i);
            }
        }
        return isNeg(a) ^ isNeg(b) ? negNum(res) : res;
    }

    private static boolean isNeg(int n) {
        return n < 0;
    }

    public static int divide(int a, int b) {
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
            return 1;
        } else if (b == Integer.MIN_VALUE) {
            return 0;
        } else if (a == Integer.MIN_VALUE) {
            if (b == negNum(1)) {
                return Integer.MAX_VALUE;
            } else {
                int c = div(add(a, 1), b);
                return add(c, div(minus(a, multi(c, b)), b));
            }
        } else {
            return div(a, b);
        }
    }

    public static void main(String[] args) {
        System.out.println( (-3 & 1) == 0);
    }
}
