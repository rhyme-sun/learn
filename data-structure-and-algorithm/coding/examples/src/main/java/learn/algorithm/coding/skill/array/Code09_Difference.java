package learn.algorithm.coding.skill.array;

/**
 * 差分数组用来解决数组的区间更新问题，以 `O(1)` 的时间复杂度更新差分数组。然后根据差分数组可以还原出原来数组区间更新后的结果。
 */
public class Code09_Difference {

    static class Diff {
        // 差分数组
        private int[] diff;
        int n;

        Diff(int[] nums) {
            n = nums.length;
            diff = new int[n];
            diff[0] = nums[0];
            for (int i = 1; i < n; i++) {
                diff[i] = nums[i] - nums[i-1];
            }
        }

        /* 给闭区间 [i, j] 增加 val（可以是负数）*/
        void update(int i, int j, int val) {
            diff[i] += val;
            if (j + 1 < n) {
                diff[j + 1] -= val;
            }
        }

        // 返回结果数组
        int[] result() {
            int[] result = new int[n];
            result[0] = diff[0];
            for (int i = 1; i < n; i++) {
                result[i] = diff[i] + result[i - 1];
            }
            return result;
        }
    }
}
