package learn.algorithm.matrix;

/**
 * 第一年农场有 1 只成熟的母牛 A，往后的每年：
 * <p>
 * - 每一只成熟的母牛都会生一只母牛；
 * - 每一只新出生的母牛都在出生的第三年成熟；
 * - 每一只母牛永远不会死
 * <p>
 * 返回 N 年后牛的数量。
 */
public class CowProblem {

    // 1 A                  1
    // 2 A B                2
    // 3 A B C              3
    // 4 A B C D            4
    // 5 A B C D E F        6
    static int num(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        // 今年牛的数量 = 去年牛的数量 + 今年新生牛的数量
        // 今年新生牛的数量 = 今年成熟牛的数量 = 3 年前牛的数量
        return num(n - 1) + num(n - 3);
    }

    static int num2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int res = 3;
        int pre = 2;
        int prepre = 1;
        int tmp1;
        int tmp2;
        for (int i = 4; i <= n; i++) {
            tmp1 = res;
            tmp2 = pre;
            res = res + prepre;
            pre = tmp1;
            prepre = tmp2;
        }
        return res;
    }

    static int num3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int[][] base = {
                {1, 1, 0},
                {0, 0, 1},
                {1, 0, 0}};
        int[][] res = MatrixUtils.matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }

    public static void main(String[] args) {
        int n = 19;
        System.out.println(num(n));
        System.out.println(num2(n));
        System.out.println(num3(n));
    }
}
