package learn.algorithm.coding.practice.p02;

/**
 * 题目描述如下：
 * 给定一个数组 arr，只能对 arr 中的一个子数组排序，但是想让 arr 整体都有序，返回满足这一设定的子数组中，最短的是多长。
 * leetcode: https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
 */
public class Code06_MinLengthForSort {

    static int findUnsortedSubarray(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int n = arr.length;
        // 找到最右侧需要排序的位置
        int r = 0;
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] >= max) {
                max = arr[i];
            } else {
                r = i;
            }
        }
        // 找到最左侧需要排序的位置
        int l = n - 1;
        int min = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] <= min) {
                min = arr[i];
            } else {
                l = i;
            }
        }
        return r - l < 0 ? 0 : r - l + 1;
    }

    public static void main(String[] args) {
        //int[] arr = {1, 2, 3, 4};
        int[] arr = {4,3,2,1};
        int ans = findUnsortedSubarray(arr);
        System.out.println(ans);
    }
}

