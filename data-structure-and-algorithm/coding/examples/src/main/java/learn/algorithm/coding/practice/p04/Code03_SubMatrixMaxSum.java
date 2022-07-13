package learn.algorithm.coding.practice.p04;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 返回一个二维数组中，子矩阵最大累加和。
 * 返回一个数组 [r1, c1, r2, c2]，其中 r1, c1 分别代表子矩阵左上角的行号和列号，r2, c2 分别代表右下角的行号和列号
 * <p>
 * leetcode: https://leetcode-cn.com/problems/max-submatrix-lcci/
 */
public class Code03_SubMatrixMaxSum {

    /**
     * 求子矩阵的最大累加和，返回最大累加和
     */
    static int maxSubMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int n = matrix.length;
        int m = matrix[0].length;

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int[] pre = new int[m];
            for (int j = i; j < n; j++) {
                pre = sum(pre, matrix[j]);
                max = Math.max(max, maxSubArray(pre));
            }
        }
        return max;
    }

    // cur 数组对应位置的值追加到 pre 数组
    private static int[] sum(int[] pre, int[] cur) {
        for (int i = 0; i < cur.length; i++) {
            pre[i] += cur[i];
        }
        return pre;
    }

    private static int maxSubArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 前一个位置最大累加和
        int pre = arr[0];
        int max = pre;
        for (int i = 1; i < arr.length; i++) {
            pre = pre > 0 ? arr[i] + pre : arr[i];
            max = Math.max(max, pre);
        }
        return max;
    }

    /**
     * 求子矩阵的最大累加和，返回子矩阵的位置
     */
    static int[] getMaxMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return null;
        }
        int max = Integer.MIN_VALUE;
        int n = matrix.length;
        int m = matrix[0].length;

        int r1 = 0, c1 = 0, r2 = 0, c2 = 0;
        for (int i = 0; i < n; i++) {
            int[] pre = new int[m];
            for (int j = i; j < n; j++) {
                pre = sum(pre, matrix[j]);
                int[] cur = getMaxSubArray(pre);
                if (cur[0] > max) {
                    max = cur[0];
                    r1 = i;
                    r2 = j;
                    c1 = cur[1];
                    c2 = cur[2];
                }
            }
        }
        return new int[]{r1, c1, r2, c2};
    }



    private static int[] getMaxSubArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        // 最大子数组范围
        int l = 0, r = 0;
        // 记录最大子数组开始的位置
        int begin = 0;
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int i = 0; i < arr.length; i++) {
            cur += arr[i];
            if (cur > max) {
                max = cur;
                r = i;
                l = begin;
            }
            if (cur < 0) {
                cur = 0;
                begin = i + 1;
            }
        }
        return new int[]{max, l, r};
    }



    public static void main(String[] args) {
        int[][] matrix = {{-4, -5}};
        int ans1 = maxSubMatrix(matrix);
        int[] ans2 = getMaxMatrix(matrix);
        System.out.println(ans1);
        ArrayComparator.printArray(ans2);
    }
}
