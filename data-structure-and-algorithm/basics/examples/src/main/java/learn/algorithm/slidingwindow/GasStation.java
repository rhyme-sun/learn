package learn.algorithm.slidingwindow;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 题目描述如下：
 * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
 * 假设你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升，
 * cost[n-1] 表示从 n 加油站到 1 加油站要消耗的汽油。
 * <p>
 * 你从其中的一个加油站出发，开始时油箱为空。
 * 给定两个整数数组 gas 和 cost ，如果你可以绕环路行驶一周（加油站序号递增方向），返回可以选择的加油站数组，
 * 对应位置为 true 表示从该加油站出发能跑完一圈，对应位置为 false 表示跑不完一圈。
 * <p>
 * leetcode 链接：https://leetcode.com/problems/gas-station
 */
public class GasStation {

    /**
     * 从可以选择的加油站中随便选择一个
     */
    static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 暴力方法，每个加油站的试着跑完一圈，看是否能跑完
     * <p>
     * 返回可以选择的加油站数组，对应位置为 true 表示从该加油站出发能跑完一圈，对应位置为 false 表示跑不完一圈。
     */
    static boolean[] goodArray(int[] gas, int[] cost) {
        int n = gas.length;
        boolean[] goodArray = new boolean[n];

        for (int i = 0; i < n; i++) {
            int curGas = gas[i];
            int nextCost = cost[i];
            // 记录加油站的编号
            int j = i;
            // 从 i 号位置开始，看能够走到那个位置（j）
            while (curGas - nextCost >= 0) {
                // 下一个加油站
                j = (j + 1) % n;
                // 走完一圈
                if (j == i) {
                    goodArray[i] = true;
                    break;
                }
                curGas = (curGas - nextCost) + gas[j];
                nextCost = cost[j];
            }
            // 下面两句是优化点
            // *************
            //  j i
            // 假设我们从加油站 i 位置开始走，走到了上述 j 位置（j<i）
            // i 到 j 之间的位置不用讨论，下面已经证明，而 j 到 i 之间的问题已经讨论过，因此此时就可以跳出整个迭代
            if (j < i) {
                return goodArray;
            }
            // *************
            //    i    j
            // 我们只能从加油站 i 走到加油站 j
            // 那么从 i 和 j 之间的加油站出发，一定不会走完一圈
            // 为什么，可以使用反证法
            // 如果假设 i 和 j 中间的位置可以走完一圈，那么从 i+1 位置一定能够走到 j+1，i 又能走到 i+1，所以从 i 位置能够 j+1，这与我们的
            // i 只能走到 j 这一条件相矛盾，所以假设不成立，i 和 j 中间的位置可不能走完一圈。
            // 因此当上述 while 循环没有走完一圈就跳出时，让 i = j，跳过 i 和 j 之间的迭代
            i = j;
        }
        return goodArray;
    }

    /**
     * 使用滑动窗口优化
     */
    static boolean[] goodArray2(int[] g, int[] c) {
        int n = g.length;
        int m = n << 1;
        // 构建滑动窗口依附的数组
        int[] arr = new int[m];
        for (int i = 0; i < n; i++) {
            arr[i] = g[i] - c[i];
            arr[i + n] = g[i] - c[i];
        }
        for (int i = 1; i < m; i++) {
            arr[i] += arr[i - 1];
        }
        LinkedList<Integer> w = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[i]) {
                w.pollLast();
            }
            w.addLast(i);
        }
        // 移动滑动窗口，从某个加油站出发是否能够走完一圈
        // 滑动窗口内的最小值减去窗口左边界前一个位置的数，得到的值如果大于 0，则说明从窗口左边界位置的加油站开始出发，能够走完一圈
        boolean[] ans = new boolean[n];
        for (int offset = 0, i = 0, j = n; j < m; offset = arr[i++], j++) {
            if (arr[w.peekFirst()] - offset >= 0) {
                ans[i] = true;
            }
            if (w.peekFirst() == i) {
                w.pollFirst();
            }
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[j]) {
                w.pollLast();
            }
            w.addLast(j);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] gas = new int[]{1, 2, 3, 4, 5};
        int[] cost = new int[]{3, 4, 5, 1, 2};
        boolean[] goodArray = goodArray(gas, cost);
        boolean[] goodArray2 = goodArray2(gas, cost);
        System.out.println(Arrays.toString(goodArray));
        System.out.println(Arrays.toString(goodArray2));
    }
}
