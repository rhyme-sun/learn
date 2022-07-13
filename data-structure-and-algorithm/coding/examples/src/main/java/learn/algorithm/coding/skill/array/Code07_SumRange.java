package learn.algorithm.coding.skill.array;

/**
 * 给定一个整数数组  nums，处理以下类型的多个查询:
 * 计算索引 left 和 right （包含 left 和 right）之间的 nums 元素的 和 ，其中 `left <= right`。
 *
 * https://leetcode.cn/problems/range-sum-query-immutable/
 */
public class Code07_SumRange {

    static class NumArray {

        // preSum[i] 0~i-1 累加和
        private int[] preSum;

        public NumArray(int[] nums) {
            this.preSum = new int[nums.length + 1];
            for (int i = 1; i < preSum.length; i++) {
                preSum[i] = preSum[i - 1] + nums[i - 1];
            }
        }

        public int sumRange(int left, int right) {
            return preSum[right + 1] - preSum[left];
        }
    }
}
