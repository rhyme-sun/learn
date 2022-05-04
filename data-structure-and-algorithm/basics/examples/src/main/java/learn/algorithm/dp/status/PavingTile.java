package learn.algorithm.dp.status;

/**
 * 题目描述如下：
 * 你有无限的 `1*2` 的砖块，要铺满 `M*N` 的区域，不同的铺法有多少种？
 */
public class PavingTile {

    /*
     * 递归尝试
     */
    static int ways1(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int[] pre = new int[M];
        for (int i = 0; i < pre.length; i++) {
            pre[i] = 1;
        }
        return process(pre, 0, N);
    }

    /**
     * 考虑当前来到 level 行，可以横着摆放瓷砖的情况，并认为 level-2 行及其之上所有行，都摆满砖了
     *
     * @param pre   level-1 行的状态
     * @param level level 行
     * @param N     一共有 N 行
     * @return 瓷砖拜访总数
     */
    private static int process(int[] pre, int level, int N) {
        if (level == N) { // base case
            for (int i = 0; i < pre.length; i++) {
                if (pre[i] == 0) {
                    return 0;
                }
            }
            return 1;
        }

        // 没到终止行，可以选择在当前的level行摆瓷砖
        int[] op = getOp(pre);
        return dfs(op, 0, level, N);
    }

    /**
     * op[i] == 0 可以考虑横着摆砖
     * op[i] == 1 只能竖着向上摆砖块
     * 使用 op 数组来讨论瓷砖摆放情况
     *
     * @param op    op
     * @param col   列号
     * @param level 行号
     * @param N     一共有 N行
     * @return 瓷砖摆放情况
     */
    private static int dfs(int[] op, int col, int level, int N) {
        // 在列上自由发挥，玩深度优先遍历，当 col 来到终止列，i 行的决定做完了
        // 轮到 i+1 行，做决定
        if (col == op.length) {
            return process(op, level + 1, N);
        }
        int ans = 0;
        // col 位置不横摆
        ans += dfs(op, col + 1, level, N);
        // col 位置向右横着摆
        if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
            op[col] = 1;
            op[col + 1] = 1;
            ans += dfs(op, col + 2, level, N);
            op[col] = 0;
            op[col + 1] = 0;
        }
        return ans;
    }

    /**
     * 根据 pre 数组构建 op 数组，pre[j] 为 1，op[j] 设置为 0，pre[j] 为 0，op[j] 设置为 1
     */
    private static int[] getOp(int[] pre) {
        int[] cur = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            cur[i] = pre[i] ^ 1;
        }
        return cur;
    }

    /**
     * 状态压缩，Min (N,M) 不超过 32
     */
    static int ways2(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        int pre = (1 << min) - 1;
        return process2(pre, 0, max, min);
    }

    private static int process2(int pre, int i, int N, int M) {
        if (i == N) { // base case
            return pre == ((1 << M) - 1) ? 1 : 0;
        }
        int op = ((~pre) & ((1 << M) - 1));
        return dfs2(op, M - 1, i, N, M);
    }

    private static int dfs2(int op, int col, int level, int N, int M) {
        if (col == -1) {
            return process2(op, level + 1, N, M);
        }
        int ans = 0;
        ans += dfs2(op, col - 1, level, N, M);
        if ((op & (1 << col)) == 0 && col - 1 >= 0 && (op & (1 << (col - 1))) == 0) {
            ans += dfs2((op | (3 << (col - 1))), col - 2, level, N, M);
        }
        return ans;
    }

    /**
     * 记忆化搜索优化
     * Min(N,M) 不超过 32
     */
    static int ways3(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        int pre = (1 << min) - 1;
        int[][] dp = new int[1 << min][max + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }
        return process3(pre, 0, max, min, dp);
    }

    private static int process3(int pre, int i, int N, int M, int[][] dp) {
        if (dp[pre][i] != -1) {
            return dp[pre][i];
        }
        int ans;
        if (i == N) {
            ans = pre == ((1 << M) - 1) ? 1 : 0;
        } else {
            int op = ((~pre) & ((1 << M) - 1));
            ans = dfs3(op, M - 1, i, N, M, dp);
        }
        dp[pre][i] = ans;
        return ans;
    }

    private static int dfs3(int op, int col, int level, int N, int M, int[][] dp) {
        if (col == -1) {
            return process3(op, level + 1, N, M, dp);
        }
        int ans = 0;
        ans += dfs3(op, col - 1, level, N, M, dp);
        if (col > 0 && (op & (3 << (col - 1))) == 0) {
            ans += dfs3((op | (3 << (col - 1))), col - 2, level, N, M, dp);
        }
        return ans;
    }

    /**
     * 严格位置依赖的动态规划解
     */
    static int ways4(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int big = N > M ? N : M;
        int small = big == N ? M : N;
        int sn = 1 << small;
        int limit = sn - 1;
        int[] dp = new int[sn];
        dp[limit] = 1;
        int[] cur = new int[sn];
        for (int level = 0; level < big; level++) {
            for (int status = 0; status < sn; status++) {
                if (dp[status] != 0) {
                    int op = (~status) & limit;
                    dfs4(dp[status], op, 0, small - 1, cur);
                }
            }
            for (int i = 0; i < sn; i++) {
                dp[i] = 0;
            }
            int[] tmp = dp;
            dp = cur;
            cur = tmp;
        }
        return dp[limit];
    }

    private static void dfs4(int way, int op, int index, int end, int[] cur) {
        if (index == end) {
            cur[op] += way;
        } else {
            dfs4(way, op, index + 1, end, cur);
            if (((3 << index) & op) == 0) { // 11 << index 可以放砖
                dfs4(way, op | (3 << index), index + 1, end, cur);
            }
        }
    }

    public static void main(String[] args) {
        int N = 8;
        int M = 6;
        System.out.println(ways1(N, M));
        System.out.println(ways2(N, M));
        System.out.println(ways3(N, M));
        System.out.println(ways4(N, M));

        N = 10;
        M = 10;
        System.out.println("=========");
        System.out.println(ways3(N, M));
        System.out.println(ways4(N, M));
    }
}
