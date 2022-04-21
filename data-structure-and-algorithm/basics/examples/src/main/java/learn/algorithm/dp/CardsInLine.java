package learn.algorithm.dp;

/**
 * 纸牌比大小问题，描述如下：
 * 给定一个整型数组 arr，代表数值不同的纸牌排成一条线，玩家 A 和玩家 B 依次拿走每张纸牌。
 * 规定玩家 A 先抽牌，玩家 B 后抽牌，但是每个玩家每次只能拿走最左或最右的纸牌，将每次拿取的点数累加到分数。
 * A 和 B 都绝顶聪明，每次都选择最优的情况拿牌，那么当抽牌结束后，请返回获胜者的分数。
 */
public class CardsInLine {

    /**
     * 方法 1，使用递归解决
     * 根据规则，返回获胜者的分数
     */
    static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int first = f1(arr, 0, arr.length - 1);
        int second = g1(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    /**
     * 在 arr[l..r] 范围内，先手获得的最好分数返回
     *
     * @return 先手拿牌最好分数
     */
    private static int f1(int[] arr, int l, int r) {
        // 最后一张牌，你是先手，直接拿走
        if (l == r) {
            return arr[l];
        }
        // 先手在拿最左侧一张牌时，最大分数就为后手在 arr[l+1,r] 内的最大分数加 arr[l]
        int p1 = arr[l] + g1(arr, l + 1, r);
        // 先手在拿最右侧一张牌时，最大分数就为后手在 arr[l,r-1] 内的最大分数加 arr[r]
        int p2 = arr[r] + g1(arr, l, r - 1);
        // 那么先手拿牌的最优解就是 p1 和 p2 的最大值
        return Math.max(p1, p2);
    }

    /**
     * 在 arr[l..r] 范围内，后手拿牌可获取的最好分数，注意，后手在先手做出了最优选择下做选择。
     *
     * @return 后手拿牌最好分数
     */
    private static int g1(int[] arr, int l, int r) {
        if (l == r) {
            // 你是后手，先手把最后一张牌拿走了，所以这里返回 0
            return 0;
        }
        // 对手拿走了 l 位置的牌，那么接下来就相当于你作为先手在 arr[l+1,r] 范围内尝试获取最大分数
        int p1 = f1(arr, l + 1, r);
        // 对手拿走了 r 位置的牌，那么接下来就相当于你作为先手在 arr[l,r-1] 范围内尝试获取最大分数
        int p2 = f1(arr, l, r - 1);
        // 因为考虑你是后手，先手最优情况下，不会留下较大的情况给你，所以这里返回 p1 和 p2 小的那一个
        return Math.min(p1, p2);
    }

    /**
     * 方法 2，使用缓存优化
     */
    static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[][] fdp = new int[n][n];
        int[][] gdp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                fdp[i][j] = -1;
                gdp[i][j] = -1;
            }
        }
        int first = f2(arr, 0, arr.length - 1, fdp, gdp);
        int second = g2(arr, 0, arr.length - 1, fdp, gdp);
        return Math.max(first, second);
    }

    /**
     *
     * 在 arr[l..r] 范围内，先手获得的最好分数返回
     *
     * @return 先手拿牌最好分数
     */
    private static int f2(int[] arr, int l, int r, int[][] fdp, int[][] gdp) {
        if (fdp[l][r] != -1) {
            return fdp[l][r];
        }
        int ans;
        if (l == r) {
            ans = arr[l];
        } else {
            int p1 = arr[l] + g2(arr, l + 1, r, fdp, gdp);
            int p2 = arr[r] + g2(arr, l, r - 1, fdp, gdp);
            ans = Math.max(p1, p2);
        }
        fdp[l][r] = ans;
        return ans;
    }

    /**
     * 在 arr[l..r] 范围内，后手拿牌可获取的最好分数，注意，后手在先手做出了最优选择下做选择。
     *
     * @return 后手拿牌最好分数
     */
    private static int g2(int[] arr, int l, int r, int[][] fdp, int[][] gdp) {
        if (gdp[l][r] != -1) {
            return gdp[l][r];
        }
        int ans = 0;
        if (l != r) {
            int p1 = f2(arr, l + 1, r, fdp, gdp);
            int p2 = f2(arr, l, r - 1, fdp, gdp);
            ans = Math.min(p1, p2);
        }
        gdp[l][r] = ans;
        return ans;
    }

    /**
     * 方法 3，最终优化
     */
    static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[][] fdp = new int[n][n];
        int[][] gdp = new int[n][n];
        for (int i = 0; i < n; i++) {
            fdp[i][i] = arr[i];
        }
        for (int startCol = 1; startCol < n; startCol++) {
            int l = 0;
            int r = startCol;
            while (r < n) {
                fdp[l][r] = Math.max(arr[l] + gdp[l + 1][r], arr[r] + gdp[l][r - 1]);
                gdp[l][r] = Math.min(fdp[l + 1][r], fdp[l][r - 1]);
                l++;
                r++;
            }
        }
        return Math.max(fdp[0][n - 1], gdp[0][n - 1]);
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9 , 4, 6};
        System.out.println(win1(arr));
        System.out.println(win2(arr));
		System.out.println(win3(arr));
    }
}
