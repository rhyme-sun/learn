package learn.algorithm.coding.practice.p16;

/**
 * 给定一个已排序的正整数数组 nums ，和一个正整数 n 。从 [1, n] 区间内选取任意个数字补充到 nums 中，使得 [1, n] 区间内的任何数字都可以用 nums 中某几个数字的和来表示。
 * 请返回 满足上述要求的最少需要补充的数字个数 。
 *
 * 链接：https://leetcode.cn/problems/patching-array
 */
public class Code03_MinPatches {

    static int minPatches(int[] nums, int n) {
        int patches = 0;
        long range = 0;
        // 使用数组提供的值扩充 range
        for (int i = 0; i < nums.length; i++) {
            while (range + 1 < nums[i]) {
                range += range + 1;
                patches++;
                if (range >= n) {
                    return patches;
                }
            }
            range += nums[i];
            if (range >= n) {
                return patches;
            }
        }
        // 覆盖 n 还至少需要添加几个数
        while (range < n) {
            range += range + 1;
            patches++;
        }
        return patches;
    }

    public static void main(String[] args) {
        int[] test = {1, 2, 31, 33};
        int n = 2147483647;
        System.out.println(minPatches(test, n));
    }
}
