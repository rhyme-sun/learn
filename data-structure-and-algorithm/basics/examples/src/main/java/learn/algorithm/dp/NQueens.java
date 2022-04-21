package learn.algorithm.dp;

/**
 * N 皇后问题
 */
public class NQueens {

    static int num1(int n) {
        if (n < 1) {
            return 0;
        }
        int[] record = new int[n];
        return process(0, record, n);
    }

    /**
     * 每一行放一个皇后，当前来到 i 行，一共是 0~n-1 行
     *
     * @param i      当前需要放置皇后的行数
     * @param record 记录 i 皇后之前皇后的放置位置，比如 record[i]=j，就表示 i 行的皇后放到了 j 列上
     * @param n      要放的皇后总数（棋盘的列数）
     * @return N 皇后放置方法数
     */
    private static int process(int i, int[] record, int n) {
        if (i == n) {
            // i 来到 n 位置，说明 n 个皇后已经按照规则放置完毕，因此得到一种放置方案
            return 1;
        }
        int res = 0;
        // 考虑皇后在第 i 行，每个列上的放置情况
        for (int j = 0; j < n; j++) {
            if (isValid(record, i, j)) {
                record[i] = j;
                res += process(i + 1, record, n);
            }
        }
        return res;
    }

    /**
     * 在 i,j 位置放皇后，根据前 i 个放皇后的位置（record 数组）判断当前位置是否合法
     *
     * @param record 以前放皇后的位置，比如 record[i]=j，就表示 i 行的皇后放到了 j 列上
     * @param i      当前皇后放置的行
     * @param j      当前皇后放置的列
     * @return 当前位置是否有效
     */
    private static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {
            // 由于每行都放一个皇后，这里近判断皇后和之前的皇后是否在同一列或者在同一个对角线即可
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 使用位运算，优化常数项时间
     * 请不要超过 32 皇后问题
     */
    static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit 最右13个1，其他都是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process(limit, 0, 0, 0);
    }

    // 7皇后问题
    // limit : 0....0 1 1 1 1 1 1 1
    // 之前皇后的列影响：colLim
    // 之前皇后的左下对角线影响：leftDiaLim
    // 之前皇后的右下对角线影响：rightDiaLim
    private static int process(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        if (colLim == limit) {
            return 1;
        }
        // pos中所有是1的位置，是你可以去尝试皇后的位置
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 7;

        long start = System.currentTimeMillis();
        System.out.println(num2(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(num1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");
    }
}
