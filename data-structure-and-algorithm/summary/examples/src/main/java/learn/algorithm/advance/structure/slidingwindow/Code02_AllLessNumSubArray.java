package learn.algorithm.advance.structure.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 问题描述如下：
 * 给定一个整型数组 arr，和一个整数 num。对于 arr 中的某个子数组 sub，
 * 如果想达标，必须满足 sub 中最大值减去 sub 中最小值小于等于 sum。返回 arr 中达标子数组的数量。
 */
public class Code02_AllLessNumSubArray {

    /**
     * 暴力方法，求出每个子数组的最大值和最小值的差值，挑选出满足要求的结果，时间复杂度 O(N^3)
     */
    static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int count = 0;
        int n = arr.length;
        for (int l = 0; l < n; l++) {
            for (int r = l; r < n; r++) {
                int max = Integer.MIN_VALUE;
                int min = Integer.MAX_VALUE;
                for (int i = l; i <= r; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    count++;
                }
            }
        }

        return count;
    }

    // 使用双端队列，存放窗口内的最大值和最小值
    static int num(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int n = arr.length;
        Deque<Integer> maxQ = new LinkedList<>();
        Deque<Integer> minQ = new LinkedList<>();
        int ans = 0;
        int left = 0, right = 0;
        while (right < n) {
            int in = arr[right];
            while (!maxQ.isEmpty() && in >= arr[maxQ.peekLast()]) {
                maxQ.pollLast();
            }
            maxQ.addLast(right);
            while (!minQ.isEmpty() && in <= arr[minQ.peekLast()]) {
                minQ.pollLast();
            }
            minQ.addLast(right);
        }
        // 扩大窗口
        right++;
        while (maxQ.peekFirst() - minQ.peekFirst() > sum) {
            ans += right - left - 1;
            if (maxQ.peekFirst() == left) {
                maxQ.pollFirst();
            }
            if (minQ.peekFirst() == left) {
                minQ.pollFirst();
            }
            // 缩小窗口
            left++;
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 1;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = num(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
