package learn.algorithm.skill.array;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 给定一个正整数组成的无序数组 arr，给定一个正整数值 K。找到 arr 中累加和为 K 且长度最大的子数组，返回其长度。
 */
public class LongestSumSubArrayLengthInPositiveArray {

    static int getMaxLength(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K <= 0) {
            return 0;
        }
        int left = 0;
        int right = 0;
        int sum = arr[0];
        int len = 0;
        while (right < arr.length) {
            if (sum == K) {
                len = Math.max(len, right - left + 1);
                sum -= arr[left++];
            } else if (sum < K) {
                sum += ++right == arr.length ? 0 : arr[right];
            } else {
                sum -= arr[left++];
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
        int value = 5;
        int testTimes = 1;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generatePositiveRandomArray(len, value);
//            int[] arr = new int[]{2, 1, 1, 1, 6, 1, 1, 1, 1, 1};
            int K = (int) (Math.random() * value) + 1;
            int ans1 = getMaxLength(arr, K);
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
