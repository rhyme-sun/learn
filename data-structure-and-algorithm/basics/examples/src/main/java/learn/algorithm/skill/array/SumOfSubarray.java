package learn.algorithm.skill.array;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 求数组全部子数组的累加和并返回
 */
public class SumOfSubarray {

    static int[] sum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int n = arr.length;
        int[] preSums = preSum(arr);
        // 子数组的个数
        int len = ((1 + n) * n >> 1) + 1;
        int[] sums = new int[len];
        int index = 1;
        // [i,j] 范围的累加和
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                sums[index++] = preSums[j + 1] - preSums[i];
            }
        }
        return sums;
    }

    /**
     * 求数组的前缀和数组，preSum[i] 表示 i 之前数组的累加和，[i,j] 位置累加和计算公式为 preSums[j+1] - preSums[i]。
     *
     * @return 前缀和数组
     */
    private static int[] preSum(int[] arr) {
        int[] preSums = new int[arr.length + 1];
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            preSums[i + 1] = sum;
        }
        return preSums;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        int[] ans = sum(arr);
        ArrayComparator.printArray(ans);
    }
}
