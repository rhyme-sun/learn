package learn.algorithm.sort.merge.usage;

/**
 * 给定一个数组 arr，和两个整数 a 和 b（a<=b），求 arr 中有多少个子数组，累加和在 [a,b] 这个范围上。返回达标的子数组数量。
 * https://leetcode.cn/problems/count-of-range-sum/
 */
public class CountOfRangeSum {

    static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (upper < lower) {
            return 0;
        }
        // 前缀和数组 sum[i] 表示 0~i 累加和
        long s = 0;
        long[] sum = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            s += nums[i];
            sum[i + 1] = s;
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    private static int process(long[] sum, int l, int r, int lower, int upper) {
        // 只有一个数，不能形成累加和，返回 0
        if (l == r) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return process(sum, l, m, lower, upper) + process(sum, m + 1, r, lower, upper)
                + merge(sum, l, m, r, lower, upper);
    }

    private static int merge(long[] sum, int l, int m, int r, int lower, int upper) {
        int ans = 0;
        // [windowL,windowR)
        int windowL = l;
        int windowR = l;
        for (int i = m + 1; i <= r; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            // 第一个大于 max 的位置
            while (windowR <= m && sum[windowR] <= max) {
                windowR++;
            }
            // 第一个不小于 min 的位置
            while (windowL <= m && sum[windowL] < min) {
                windowL++;
            }
            ans += windowR - windowL;
        }

        long[] help = new long[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            help[index++] = sum[p1] <= sum[p2] ? sum[p1++] : sum[p2++];
        }
        while (p1 <= m) {
            help[index++] = sum[p1++];
        }
        while (p2 <= r) {
            help[index++] = sum[p2++];
        }
        for (int i = 0; i < help.length; i++) {
            sum[l + i] = help[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {-2, 5, -1};
        int a = -2;
        int b = 2;
        int ans = countRangeSum(arr, a, b);
        System.out.println(ans);
    }
}
