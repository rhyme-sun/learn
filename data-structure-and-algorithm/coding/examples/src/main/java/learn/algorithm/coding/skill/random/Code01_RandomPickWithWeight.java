package learn.algorithm.coding.skill.random;

import java.util.Comparator;
import java.util.Random;

/**
 * 给你一个 下标从 0 开始 的正整数数组 w ，其中 `w[i]` 代表第 i 个下标的权重。
 * 请你实现一个函数 `pickIndex` ，它可以 随机地 从范围 `[0, w.length - 1]` 内（含 0 和 `w.length - 1`）选出并返回一个下标。
 * 选取下标 i 的 概率 为 `w[i] / sum(w)` 。
 * 例如，对于 `w = [1, 3]`，挑选下标 0 的概率为 `1 / (1 + 3) = 0.25` ，而选取下标 1 的概率为 `3 / (1 + 3) = 0.75`。
 * <p>
 * https://leetcode.cn/problems/random-pick-with-weight
 */
public class Code01_RandomPickWithWeight {

    static class Solution {
        // preSum[i] 表示 [0,i] 范围累加和
        private int[] preSum;
        private int sum;

        public Solution(int[] w) {
            preSum = new int[w.length];
            int sum = 0;
            for (int i = 0; i < w.length; i++) {
                sum += w[i];
                preSum[i] = sum;
            }
            this.sum = sum;
        }

        // [1,3]
        // 0~3 的随机数 小于 1 的概率为 1/4 >=1 <=4 的概率为 3/4
        // [1,]
        public int pickIndex() {
            int target = (int) (Math.random() * sum);
            return ceiling(preSum, target);
        }

        // 大于 target 的最小值
        private int ceiling(int[] arr, int target) {
            int left = 0, right = arr.length - 1;
            int index = -1;
            while (left <= right) {
                int mid = (left + right) / 2;
                if (arr[mid] > target) {
                    right = mid - 1;
                    index = mid;
                } else {
                    left = mid + 1;
                }
            }
            return index;
        }
    }

    public static void main(String[] args) {
//        int[] w = {1, 3};
//        Solution solution = new Solution(w);
//        for (int i = 0; i < 10; i++) {
//            System.out.println(solution.pickIndex());
//        }
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(1));
        }
    }
}
