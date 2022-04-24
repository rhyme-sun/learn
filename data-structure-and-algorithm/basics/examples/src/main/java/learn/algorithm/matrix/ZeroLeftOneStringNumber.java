package learn.algorithm.matrix;

/**
 * 问题描述如下：
 * 给定一个数 N，想象只由 0 和 1 两种字符，组成的所有长度为 N 的字符串。如果某个字符串，任何 0 字符的左边都有 1 紧挨着，
 * 认为这个字符串达标。返回有多少达标的字符串。
 */
public class ZeroLeftOneStringNumber {

    /**
     * 方法 1：递归尝试
     * 用剩下的 n 个字符去组成字符串，且前一个字符（第 n-1 个）一定为 1，因为 0 组成的字符串一定不达标，返回达标的字符串个数
     *
     * @return 标的字符串个数
     */
    static int num1(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        // 可能性 1：当前位置用 1 字符
        int p1 = num1(n - 1);
        // 可能性 2：当前位置用 0 字符，则下个字符一定要用 1 字符
        int p2 = num1(n - 2);
        return p1 + p2;
    }

    /**
     * 方法 2：迭代方法，时间复杂度 O(N)
     */
    static int num2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int res = 2;
        int pre = 1;
        int tmp;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    /**
     * 方法 3：矩阵幂优化
     * 时间复杂度  O(logN)
     */
    static int num3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = {
                {1, 1},
                {1, 0}
        };
        int[][] res = MatrixUtils.matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    public static void main(String[] args) {
        int i = 20;
        System.out.println(num1(i));
        System.out.println(num2(i));
        System.out.println(num3(i));
    }
}
