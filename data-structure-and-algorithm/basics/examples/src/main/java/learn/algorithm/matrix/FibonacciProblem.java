package learn.algorithm.matrix;

/**
 * 斐波那契数列矩阵乘法
 */
public class FibonacciProblem {

    /**
     * 方法 1：递归求解斐波那契数列第 n 项
     */
    static int f1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return f1(n - 1) + f1(n - 2);
    }

    /**
     * 方法 2：迭代求斐波那契数列第 n 项
     * 时间复杂度 O(N)
     */
    static int f2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int res = 1;
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
     * 方法 3：代数公式求斐波那契第 n 项
     * 时间复杂度  O(logN)
     */
    static int f3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        // 项数矩阵
        // [ 1 ,1 ]
        // [ 1, 0 ]
        int[][] base = {
                {1, 1},
                {1, 0}
        };
        int[][] res = MatrixUtils.matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }

    public static void main(String[] args) {
        int n = 19;
        System.out.println(f1(n));
        System.out.println(f2(n));
        System.out.println(f3(n));
    }
}
