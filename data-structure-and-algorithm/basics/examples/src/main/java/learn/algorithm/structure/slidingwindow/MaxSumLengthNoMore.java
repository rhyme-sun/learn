package learn.algorithm.structure.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定一个数组 arr，和一个正数 M，返回在 arr 的子数组在长度不超过 M 的情况下，最大的累加和。
 */
public class MaxSumLengthNoMore {

    /**
     * 暴力解
     */
    static int maxSum1(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 1) {
            return 0;
        }
        int N = arr.length;
        int max = Integer.MIN_VALUE;
        for (int L = 0; L < N; L++) {
            int sum = 0;
            for (int R = L; R < N; R++) {
                if (R - L + 1 > m) {
                    break;
                }
                sum += arr[R];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    static int maxSum(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 1) {
            return 0;
        }
        int n = arr.length;
        m = Math.min(n, m);
        // 构建一个前缀和数组 sum，sum[i] 表示数组 i 和 i 之前元素的累加和
        int[] sums = new int[n];
        sums[0] = arr[0];
        for (int i = 1; i < n; i++) {
            sums[i] += sums[i - 1] + arr[i];
        }
        // 窗口右侧移动到 m 位置
        Deque<Integer> maxQ = new LinkedList<>();
        for (int r = 0; r < m; r++) {
            // 弹出队列尾部元素，直到队列为空或者尾部元素大于要入队的元素
            while (!maxQ.isEmpty() && sums[maxQ.peekLast()] <= sums[r]) {
                maxQ.pollLast();
            }
            maxQ.addLast(r);
        }
        int maxSum = sums[maxQ.peekFirst()];
        // l 指向窗口左侧前一个位置
        int l = 0;
        for (int r = m; r < n; r++, l++) {
            // 弹出队列尾部元素，直到队列为空或者尾部元素大于要入队的元素
            while (!maxQ.isEmpty() && sums[maxQ.peekLast()] <= sums[r]) {
                maxQ.pollLast();
            }
            maxQ.addLast(r);
            if (maxQ.peekFirst() == l) {
                maxQ.pollFirst();
            }
            int cur = sums[maxQ.peekFirst()] - sums[l];
            maxSum = Math.max(maxSum, cur);
        }
        // 处理队列里剩余元素
        for (; l < n - 1; l++) {
            if (maxQ.peekFirst() == l) {
                maxQ.pollFirst();
            }
            int cur = sums[maxQ.peekFirst()] - sums[l];
            maxSum = Math.max(maxSum, cur);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 100;
        int maxValue = 50;
        int maxM = 10;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxSize, maxValue);
            int m = (int) (Math.random() * maxM + 1);
            int ans1 = maxSum(arr, m);
            int ans2 = maxSum1(arr, m);
            if (ans1 != ans2) {
                System.out.println(m);
                ArrayComparator.printArray(arr);
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
    }
}
