package learn.algorithm.advance.structure.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定一个数组 arr，和一个正数 M，返回在 arr 的子数组在长度不超过 M 的情况下，最大的累加和。
 */
public class Code05_MaxSumLengthNoMore {

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

    static int maxSum2(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w < 1) {
            return 0;
        }
        int n = arr.length;
        int[] sums = new int[n];
        sums[0] = arr[0];
        for (int i = 1; i < n; i++) {
            sums[i] += sums[i - 1] + arr[i];
        }
        w = Math.min(n, w);
        int ans = Integer.MIN_VALUE;
        int left = 0, right = 0;

        Deque<Integer> deque = new LinkedList<>();
        while (right < n) {
            int in = sums[right];
            while (!deque.isEmpty() && in >= sums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.addLast(right);
            right++;

            while (right - left == w) {
                // 左侧前一个值
                int beforeLeft = left == 0 ? 0 : sums[left - 1];
                ans = Math.max(ans, sums[deque.peekFirst()] - beforeLeft);
                if (left == deque.peekFirst()) {
                    deque.pollFirst();
                }
                left++;
            }
        }
        // 处理剩余部分
        while (left < n) {
            ans = Math.max(ans, sums[deque.peekFirst()] - sums[left - 1]);
            if (deque.peekFirst() == left) {
                deque.pollFirst();
            }
            left++;
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 100;
        int maxValue = 50;
        int maxM = 10;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxSize, maxValue);
             int m = (int) (Math.random() * maxM + 1);
            int ans1 = maxSum1(arr, m);
            int ans2 = maxSum2(arr, m);
            if (ans1 != ans2) {
                System.out.println(m);
                ArrayComparator.printArray(arr);
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
