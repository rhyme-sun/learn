package learn.algorithm.coding.practice.p13;

/**
 * 假设有 n 台超级洗衣机放在同一排上。开始的时候，每台洗衣机内可能有一定量的衣服，也可能是空的。
 * 在每一步操作中，你可以选择任意 m (`1<=m<=n`) 台洗衣机，与此同时将每台洗衣机的一件衣服送到相邻的一台洗衣机。
 * 给定一个整数数组 machines 代表从左至右每台洗衣机中的衣物数量，请给出能让所有洗衣机中剩下的衣物的数量相等的最少的操作步数 。
 * 如果不能使每台洗衣机中衣物的数量相等，则返回 -1 。
 * <p>
 * 链接：https://leetcode.cn/problems/super-washing-machines
 */
public class Code03_SuperWashingMachines {

    static int findMinMoves(int[] machines) {
        if (machines == null || machines.length == 0) {
            return 0;
        }
        int n = machines.length;
        int sum = 0;
        // sums[i] [0,i-1] 前缀和
        // sum[i,j] = sums[j+1]-sum[i]
        int sums[] = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum += machines[i - 1];
            sums[i] = sum;
        }
        if (sum % n != 0) {
            return -1;
        }
        int avg = sum / n;

        int ans = Integer.MIN_VALUE;
        // [0,i-1] i [i+1, n-1]
        for (int i = 0; i < n; i++) {
            // 左侧多余衣服数量
            int L = (sums[i] - sums[0]) - avg * i;
            // 右侧多余衣服数量
            int R = (sums[n] - sums[i + 1]) - avg * (n - 1 - i - 1 + 1);
            int min = 0;
            if (L < 0 && R < 0) {
                min = Math.abs(L) + Math.abs(R);
            } else {
                min = Math.max(Math.abs(L), Math.abs(R));
            }
            ans = Math.max(ans, min);
        }
        return ans;
    }
}
