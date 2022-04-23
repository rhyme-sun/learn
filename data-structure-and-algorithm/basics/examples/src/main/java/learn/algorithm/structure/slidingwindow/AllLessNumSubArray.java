package learn.algorithm.structure.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 问题描述如下：
 * 给定一个整型数组 arr，和一个整数 num。对于 arr 中的某个子数组 sub，
 * 如果想达标，必须满足 sub 中最大值减去 sub 中最小值小于等于 sum。返回 arr 中达标子数组的数量。
 */
public class AllLessNumSubArray {

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

    /**
     * 使用双端队列，存放窗口内的最大值和最小值
     */
    static int num(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int n = arr.length;
        int count = 0;
        Deque<Integer> maxQ = new LinkedList<>();
        Deque<Integer> minQ = new LinkedList<>();
        int r = 0;
        for (int l = 0; l < n; l++) {
            // 窗口右边界向右移动，直到窗口内的元素不满足要求
            while (r < n) {
                while (!maxQ.isEmpty() && arr[maxQ.peekLast()] <= arr[r]) {
                    maxQ.pollLast();
                }
                maxQ.addLast(r);
                while (!minQ.isEmpty() && arr[minQ.peekLast()] >= arr[r]) {
                    minQ.pollLast();
                }
                minQ.addLast(r);
                if (arr[maxQ.peekFirst()] - arr[minQ.peekFirst()] > sum) {
                    break;
                } else {
                    r++;
                }
            }
            count += r - l;
            // 窗口左边界向右移动
            // 淘汰队列内过期的值（左边界向右移动时，表示该元素从窗口移出，如果队列中的队首元素如果恰好是要移出的元素（即过期了）则从队首弹出）
            if (maxQ.peekFirst() == l) {
                maxQ.pollFirst();
            }
            if (minQ.peekFirst() == l) {
                minQ.pollFirst();
            }
        }
        return count;
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
