package learn.algorithm.practice.p07;

// 测试链接 :

/**
 * 题目描述如下：
 * 给定一个数组 arr，返回如果排序之后，相邻两数的最大差值。要求时间复杂度 `O(N)`。
 * <p>
 * leetcode:  https://leetcode.cn/problems/maximum-gap/
 */
public class Code03_MaxGap {

    public static int maximumGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int len = nums.length;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        if (min == max) {
            return 0;
        }
        boolean[] hasNum = new boolean[len + 1];
        int[] maxs = new int[len + 1];
        int[] mins = new int[len + 1];
        for (int i = 0; i < len; i++) {
            int bid = bucket(nums[i], len, min, max);
            mins[bid] = hasNum[bid] ? Math.min(mins[bid], nums[i]) : nums[i];
            maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], nums[i]) : nums[i];
            hasNum[bid] = true;
        }
        int ans = 0;
        // 上个不为空的桶的最大值
        int lastMax = maxs[0];
        for (int i = 1; i <= len; i++) {
            if (hasNum[i]) {
                // 计算桶之间相邻两个数的最大差值：桶的最小值 - 上一个不为空的桶的最大值
                ans = Math.max(ans, mins[i] - lastMax);
                lastMax = maxs[i];
            }
        }
        return ans;
    }

    /**
     * 计算 num 所在桶的编号
     *
     * @param num num
     * @param len 数组长度
     * @param min 数组元素的最小值
     * @param max 数组元素的最大值
     * @return num 所在桶的编号
     */
    private static int bucket(long num, long len, long min, long max) {
        // 每个桶的范围：(max - min) / len
        // 桶的编号：(num - min) / ((max - min) / (len +  1)) -> (num - min) * (len + 1) / (max - min)
        int ans = (int) ((num - min) * (len + 1) / (max - min));
        return num == max ? ans - 1 : ans;
        //return (int) ((num - min) * (len) / (max - min));
    }

    public static void main(String[] args) {
        int[] arr = {3, 6, 9, 1};
        int ans = maximumGap(arr);
        System.out.println(ans);
    }
}
