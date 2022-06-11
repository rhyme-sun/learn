package learn.algorithm.basics.algorithm.sort.merge.usage;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 在一个数组中，对于每个数 num，求有多少个后面的数乘 2 依然小于 num，求总个数。
 * 比如：`[3,1,7,0,2]`
 * <p>
 * - 3 的后面有：1、0；
 * - 1 的后面有：0；
 * - 7 的后面有：0、2；
 * - 0 的后面没有；
 * - 2 的后面没有；
 * <p>
 * 所以总共有 5 个。
 */
public class Code03_BiggerThanRightTwice {

    static int biggerTwice(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] * 2 < arr[i]) {
                    ans++;
                }
            }
        }
        return ans;
    }

    static int biggerTwice2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return process(arr, l, m) + process(arr, m + 1, r) + merge(arr, l, m, r);
    }

    private static int merge(int[] arr, int l, int m, int r) {
        int ans = 0;
        int windowL = l;
        int windowR = m + 1;
        while (windowL <= m && windowR <= r) {
            if (arr[windowL] > arr[windowR] * 2) {
                ans += m - windowL + 1;
                windowR++;
            } else {
                windowL++;
            }
        }
        int[] help = new int[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[index++] = arr[p1++];
        }
        while (p2 <= r) {
            help[index++] = arr[p2++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 50;
        int maxValue = 20;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generatePositiveRandomArray(maxSize, maxValue);
            int ans1 = biggerTwice(arr);
            int ans2 = biggerTwice2(ArrayComparator.copyArray(arr));
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
