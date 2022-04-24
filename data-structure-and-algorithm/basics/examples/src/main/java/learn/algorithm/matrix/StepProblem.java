package learn.algorithm.matrix;

/**
 * 迈台阶。
 * 问题描述如下：
 * 一个人可以一次往上迈 1 个台阶，也可以迈 2 个台阶，返回这个人迈上 n 级台阶的方法数。
 */
public class StepProblem {

    /**
     * 递归方法
     */
    static int steps1(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        // 第一种可能，迈 1 个台阶
        int p1 = steps1(n - 1);
        // 第二种可能，迈 2 个台阶
        int p2 = steps1(n - 2);
        return p1 + p2;
    }

    /**
     * 改成迭代方法
     */
    static int steps2(int n) {
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
     * 使用代数矩阵优化
     */
    static int steps3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = MatrixUtils.matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    public static void main(String[] args) {
        int n = 20;
        System.out.println(steps1(n));
        System.out.println(steps2(n));
        System.out.println(steps3(n));
    }
}
