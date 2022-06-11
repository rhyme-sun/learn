package learn.algorithm.advance.algorithm.array;

import java.util.HashMap;
import java.util.Map;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定一个整数组成的无序数组 arr，值可能正、可能负、可能 0，给定一个正整数值 K。找到 arr 中累加和为 K 且长度最大的子数组，返回其长度。
 */
public class Code02_LongestSumSubArrayLength {

    static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // key 为前缀和（包含自己），value 为前缀和首次出现的位置
        // 注意：需要设置 (0,-1) 这一项，否则会错过子数组起始位置为 0 的答案
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // important
        int len = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum - k)) {
                len = Math.max(i - map.get(sum - k), len);
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return len;
    }

    // for test
    static int right(int[] arr, int K) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(arr, i, j, K)) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    // for test
    private static boolean valid(int[] arr, int L, int R, int K) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == K;
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTimes = 500000;

        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generateRandomArray(len, value);
            int K = (int) (Math.random() * value) - (int) (Math.random() * value);
            int ans1 = maxLength(arr, K);
            int ans2 = right(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println("K : " + K);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
