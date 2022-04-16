package learn.algorithm.greedy;

import java.util.Arrays;
import java.util.PriorityQueue;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 求金条分割的最小代价，问题描述如下：
 * 一块金条切成两半，是需要花费和长度数值一样的铜板，比如长度为 20 的金条，不管怎么切，都要花费 20个铜板。
 * 现有一群人想整分整块金条，怎么分最省铜板？
 * 例如，给定数组 [10, 20, 30]，代表一共三个人（分成三块），整块金条长度为 60，金条要分成 10、20、30 三个部分。
 * 如果先把长度 60 的金条分成 10 和 50，花费 60；再把长度 50 的金条分成 20 和 30，花费 50，这样一共花费 110 铜板。
 */
public class LessMoneySplitGold {

    /**
     * 暴力递归
     */
    static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    /**
     * 两两合并数组元素的值
     *
     * @param arr 等待合并的数组
     * @param pre 之前的合并行为产生了多少总代价
     * @return 最小的总代价
     */
    private static int process(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    private static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    /**
     * 贪心算法，使用堆
     */
    static int lessMoney2(int[] arr) {
        PriorityQueue<Integer> pQ = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pQ.add(arr[i]);
        }
        int sum = 0;
        int cur;
        while (pQ.size() > 1) {
            cur = pQ.poll() + pQ.poll();
            sum += cur;
            pQ.add(cur);
        }
        return sum;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 6;
        int maxValue = 100;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generateRandomArray2(maxSize, maxValue);
            final int i1 = lessMoney1(arr);
            final int i2 = lessMoney2(arr);
            if (i1 != i2) {
                System.out.println("Oops!");
                System.out.println(i1);
                System.out.println(i2);
                ArrayComparator.printArray(arr);
            }
        }
        System.out.println("finish!");
    }
}
