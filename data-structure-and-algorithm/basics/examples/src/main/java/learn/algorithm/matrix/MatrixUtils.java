package learn.algorithm.matrix;

/**
 * 矩阵运算工具类
 */
public class MatrixUtils {

    /**
     * 求矩阵的幂
     *
     * @param m 矩阵
     * @param p 指数
     * @return 矩阵的 p 次幂
     */
    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        // 构建单位矩阵
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // res = 矩阵中的 1
        int[][] t = m;// 矩阵 1 次方
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = muliMatrix(res, t);
            }
            t = muliMatrix(t, t);
        }
        return res;
    }

    /**
     * 矩阵相乘
     */
    private static int[][] muliMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
}
