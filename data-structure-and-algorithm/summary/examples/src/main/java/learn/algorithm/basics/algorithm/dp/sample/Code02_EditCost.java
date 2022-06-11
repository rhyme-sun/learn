package learn.algorithm.basics.algorithm.dp.sample;

public class Code02_EditCost {

    /**
     * s1 到 s2 最小编辑代价
     * <p>
     * a 新增代价
     * c 修改代价
     * d 删除代价
     */
    static int minEditCost(String s1, String s2, int a, int d, int c) {
        if (s1 == null || s2 == null) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int n = str1.length;
        int m = str2.length;

        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = i * d;
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = j * a;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int p1 = dp[i - 1][j] + d;
                int p2 = dp[i][j - 1] + a;
                int p3 = str1[i - 1] == str2[j - 1] ? dp[i - 1][j - 1] : dp[i - 1][j - 1] + c;
                dp[i][j] = Math.min(Math.min(p1, p2), p3);
            }
        }
        return dp[n][m];
    }

    /**
     * 空间压缩
     */
    static int minEditCost2(String str1, String str2, int a, int d, int c) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
        char[] shorts = chs1.length < chs2.length ? chs1 : chs2;
        if (chs1.length < chs2.length) {
            int tmp = a;
            a = d;
            d = tmp;
        }
        int[] dp = new int[shorts.length + 1];
        for (int i = 1; i <= shorts.length; i++) {
            dp[i] = a * i;
        }
        for (int i = 1; i <= longs.length; i++) {
            int pre = dp[0];
            dp[0] = d * i;
            for (int j = 1; j <= shorts.length; j++) {
                int tmp = dp[j];
                if (longs[i - 1] == shorts[j - 1]) {
                    dp[j] = pre;
                } else {
                    dp[j] = pre + c;
                }
                dp[j] = Math.min(dp[j], dp[j - 1] + a);
                dp[j] = Math.min(dp[j], tmp + d);
                pre = tmp;
            }
        }
        return dp[shorts.length];
    }

    /**
     * 只能删除（代价为 1），求最小删除代价
     * 如果无法完成，返回 Integer.MAX_VALUE
     */
    static int onlyDelete(char[] x, char[] y) {
        if (x.length < y.length) {
            return Integer.MAX_VALUE;
        }
        int N = x.length;
        int M = y.length;
        int[][] dp = new int[N + 1][M + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[0][0] = 0;
        // dp[i][j]表示前缀长度
        for (int i = 1; i <= N; i++) {
            dp[i][0] = i;
        }
        for (int xlen = 1; xlen <= N; xlen++) {
            for (int ylen = 1; ylen <= Math.min(M, xlen); ylen++) {
                if (dp[xlen - 1][ylen] != Integer.MAX_VALUE) {
                    dp[xlen][ylen] = dp[xlen - 1][ylen] + 1;
                }
                if (x[xlen - 1] == y[ylen - 1] && dp[xlen - 1][ylen - 1] != Integer.MAX_VALUE) {
                    dp[xlen][ylen] = Math.min(dp[xlen][ylen], dp[xlen - 1][ylen - 1]);
                }
            }
        }
        return dp[N][M];
    }


    public static void main(String[] args) {
        String str1 = "ab12cd3";
        String str2 = "abcdf";
        System.out.println(minEditCost(str1, str2, 5, 3, 2));
        System.out.println(minEditCost2(str1, str2, 5, 3, 2));
        System.out.println();

        str1 = "abcdf";
        str2 = "ab12cd3";
        System.out.println(minEditCost(str1, str2, 3, 2, 4));
        System.out.println(minEditCost2(str1, str2, 3, 2, 4));
        System.out.println();

        str1 = "";
        str2 = "ab12cd3";
        System.out.println(minEditCost(str1, str2, 1, 7, 5));
        System.out.println(minEditCost2(str1, str2, 1, 7, 5));
        System.out.println();

        str1 = "abcdf";
        str2 = "";
        System.out.println(minEditCost(str1, str2, 2, 9, 8));
        System.out.println(minEditCost2(str1, str2, 2, 9, 8));
    }
}
