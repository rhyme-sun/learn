package learn.algorithm.structure.monotonousstack;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定一个数组 arr，返回所有子数组最小值的累加和。
 * leetcode 链接：https://leetcode.com/problems/sum-of-subarray-minimums/
 */
public class SumOfSubarrayMinimums {

    /**
     * 暴力解
     */
    static int subArrayMinSum1(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int min = arr[i];
                for (int k = i + 1; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                }
                ans += min;
            }
        }
        return ans;
    }

    /**
     * 最优解，没有用单调栈
     */
    static int subArrayMinSum2(int[] arr) {
        // 求出每个元素左侧最近最小位置
        int[] left = leftNearLessEqual2(arr);
        // 求出每个元素右侧侧最近最小位置
        int[] right = rightNearLess2(arr);
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            int start = i - left[i];
            int end = right[i] - i;
            ans += start * end * arr[i];
        }
        return ans;
    }

    private static int[] leftNearLessEqual2(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            int ans = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] <= arr[i]) {
                    ans = j;
                    break;
                }
            }
            left[i] = ans;
        }
        return left;
    }

    private static int[] rightNearLess2(int[] arr) {
        int n = arr.length;
        int[] right = new int[n];
        for (int i = 0; i < n; i++) {
            int ans = n;
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) {
                    ans = j;
                    break;
                }
            }
            right[i] = ans;
        }
        return right;
    }

    /**
     * 最优解思路下的单调栈优化
     */
    static int sumSubarrayMins(int[] arr) {
        int[] stack = new int[arr.length];
        int[] left = nearLessEqualLeft(arr, stack);
        int[] right = nearLessRight(arr, stack);
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            long start = i - left[i];
            long end = right[i] - i;
            ans += start * end * (long) arr[i];
            ans %= 1000000007;
        }
        return (int) ans % 1000000007;
    }

    private static int[] nearLessEqualLeft(int[] arr, int[] stack) {
        int n = arr.length;
        int[] left = new int[n];
        int size = 0;
        for (int i = n - 1; i >= 0; i--) {
            while (size != 0 && arr[i] <= arr[stack[size - 1]]) {
                left[stack[--size]] = i;
            }
            stack[size++] = i;
        }
        while (size != 0) {
            left[stack[--size]] = -1;
        }
        return left;
    }

    private static int[] nearLessRight(int[] arr, int[] stack) {
        int n = arr.length;
        int[] right = new int[n];
        int size = 0;
        for (int i = 0; i < n; i++) {
            while (size != 0 && arr[stack[size - 1]] > arr[i]) {
                right[stack[--size]] = i;
            }
            stack[size++] = i;
        }
        while (size != 0) {
            right[stack[--size]] = n;
        }
        return right;
    }

    static int sumSubarrayMins2(int[] arr) {
        int[] stack = new int[arr.length];
        // 指向栈顶
        int si = -1;
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            while (si != -1 && arr[i] <= arr[stack[si]]) {
                int pop = stack[si--];
                int rightLessIndex = i;
                int leftLessIndex = si == -1 ? -1 : stack[si];
                sum += (rightLessIndex - pop) * (pop - leftLessIndex) * (long) arr[pop];
                sum %= 1000000007;
            }
            stack[++si] = i;
        }
        while (si != -1) {
            int pop = stack[si--];
            int rightLessIndex = arr.length;
            int leftLessIndex = si == -1 ? -1 : stack[si];
            sum += (rightLessIndex - pop) * (pop - leftLessIndex) * (long) arr[pop];
            sum %= 1000000007;
        }
        return (int) sum % 1000000007;
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 50;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = ArrayComparator.generatePositiveRandomArray(len, maxValue);
            int ans1 = subArrayMinSum1(arr);
            int ans2 = subArrayMinSum2(arr);
            int ans3 = sumSubarrayMins2(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops");
                ArrayComparator.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
