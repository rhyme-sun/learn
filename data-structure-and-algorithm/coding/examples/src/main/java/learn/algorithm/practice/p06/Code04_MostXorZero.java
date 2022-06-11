package learn.algorithm.practice.p06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 问题描述如下：
 * <p>
 * 数组中所有数都异或起来的结果，叫做异或和，给定一个数组 arr，可以任意切分成若干个不相交的子数组。
 * 其中一定存在一种最优方案，使得切出异或和为 0 的子数组最多，返回这个最多数量。
 */
public class Code04_MostXorZero {

    /**
     * 暴力方法，时间复杂度 O(2^N)
     */
    static int mostXorZero(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] pre = new int[n];
        int xor = 0;
        for (int i = 0; i < n; i++) {
            xor ^= arr[i];
            pre[i] = xor;

        }
        return process1(pre, 0, new ArrayList<>());
    }

    /**
     * 当前来到 index 位置，考虑要不要在 index 后面切一刀，返回异或和为 0 的最多数量
     *
     * @param xor   前缀异或和
     * @param index index
     * @param pos   记录切割位置
     * @return 最多数量
     */
    private static int process1(int[] xor, int index, ArrayList<Integer> pos) {
        if (index == xor.length - 1) {
            return countXorZero(xor, pos);
        }
        // index 后不切
        int p1 = process1(xor, index + 1, pos);
        // index 后切一刀
        pos.add(index);
        int p2 = process1(xor, index + 1, pos);
        pos.remove(pos.size() - 1);
        return Math.max(p1, p2);
    }

    /**
     * 根据切割位置计算出异或和为 0 的个数
     */
    private static int countXorZero(int[] xor, List<Integer> pos) {
        int count = 0;
        // 上次切割位置的前缀异或和
        int prePosXor = 0;
        for (int i = 0; i < pos.size(); i++) {
            int curPos = pos.get(i);
            if ((xor[curPos] ^ prePosXor) == 0) {
                count++;
            }
            prePosXor = xor[curPos];
        }
        // 最后一个切割位置到结尾那一部分的异或和
        if ((xor[xor.length - 1] ^ prePosXor) == 0) {
            count++;
        }
        return count;
    }

    /**
     * 时间复杂度 O(N) 的方法
     */
    static int mostXorZero2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        // dp[i] 表示 arr[0..i] 异或和为 0 的最多个数
        int[] dp = new int[N];

        // key 某一个前缀异或和
        // value 这个前缀异或和上次出现的位置
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // import
        int xor = 0;
        for (int i = 0; i < N; i++) {
            xor ^= arr[i];
            // 最后一部分异或和为 0
            if (map.containsKey(xor)) {
                int pre = map.get(xor);
                dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
            }
            // 最后一部分异或和不为 0
            if (i > 0) {
                dp[i] = Math.max(dp[i - 1], dp[i]);
            }
            map.put(xor, i);
        }
        return dp[N - 1];
    }

    // for test
    public static void main(String[] args) {
        int testTime = 15000;
        int maxSize = 12;
        int maxValue = 5;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayComparator.generatePositiveRandomArray(maxSize, maxValue);
            int ans1 = mostXorZero(arr);
            int ans2 = mostXorZero2(arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
