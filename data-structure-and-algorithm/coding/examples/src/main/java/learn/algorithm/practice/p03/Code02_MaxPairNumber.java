package learn.algorithm.practice.p03;

import java.util.Arrays;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定一个数组 arr，代表每个人的能力值。再给定一个非负数 k，如果两个人能力差值正好为 k，那么可以凑在一起比赛。
 * 一局比赛只有两个人，返回最多可以同时有多少场比赛。
 */
public class Code02_MaxPairNumber {

    /**
     * 暴力解，枚举所有全排列结果，找到最优解（注意，如果数组太长，代码会跑很久）
     */
    static int right(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k < 0) {
            return -1;
        }
        return process(arr, 0, k);
    }

    /**
     * 全排列找最优解
     */
    private static int process(int[] arr, int index, int k) {
        // 得到了一种排列结果
        if (index == arr.length) {
            int ans = 0;
            for (int i = 1; i < arr.length; i += 2) {
                if (arr[i] - arr[i - 1] == k) {
                    ans++;
                }
            }
            return ans;
        }
        int max = Integer.MIN_VALUE;
        for (int i = index; i < arr.length; i++) {
            ArrayComparator.swap(arr, index, i);
            max = Math.max(max, process(arr, index + 1, k));
            ArrayComparator.swap(arr, i, index);
        }
        return max;
    }

    static int maxPairNumber(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k < 0) {
            return -1;
        }
        Arrays.sort(arr);
        int n = arr.length;
        int l = 0, r = 0;

        int ans = 0;
        // 记录窗口右边界是否使用过
        boolean[] usedR = new boolean[n];
        while (r < n) {
            if (usedR[l] == true) {
                l++;
            } else if (l >= r) {
                r++;
            } else {
                int minus = arr[r] - arr[l];
                if (minus == k) {
                    ans++;
                    l++;
                    usedR[r++] = true;
                } else if (minus > k) {
                    l++;
                } else {
                    r++;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTimes = 1000;
        int maxSize = 10;
        int maxValue = 10;

        for (int i = 0; i < testTimes; i++) {
            int k = (int) (Math.random() * maxValue);
            int[] arr = ArrayComparator.generatePositiveRandomArray(maxSize, maxValue);
            int ans1 = right(arr, k);
            int ans2 = maxPairNumber(arr, k);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println(k);
                ArrayComparator.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
