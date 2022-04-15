package learn.algorithm.sort.bucket;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import learn.algorithm.sort.SortTestUtils;

/**
 * RadixSort 基数排序
 */
public class RadixSort {


    /**
     * 该方法只能排序正整数，使用队列作为桶
     */
    static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    /**
     * 在数组 l~r 范围上进行基数排序，digit 表示最大数十进制位数
     */
    private static void radixSort(int[] arr, int l, int r, int digit) {
        // 有多少个数准备多少个辅助空间
        int[] help = new int[r - l + 1];
        final List<LinkedList<Integer>> buckets = createBuckets();
        for (int d = 1; d <= digit; d++) {
            for (int i : arr) {
                final LinkedList<Integer> bucket = buckets.get(getDigit(i, d));
                bucket.add(i);
            }
            int index = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    help[index++] = bucket.poll();
                }
            }
            for (int i = l, j = 0; i <= r; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    private static List<LinkedList<Integer>> createBuckets() {
        List<LinkedList<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            buckets.add(new LinkedList<>());
        }
        return buckets;
    }

    /**
     * 桶排序的优化，不使用队列作为桶，而是借助计数数组来实现
     */
    static void radixSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }


    /**
     * 在数组 l~r 范围上进行基数排序，digit 表示最大数十进制位数
     */
    private static void radixSort2(int[] arr, int l, int r, int digit) {
        final int radix = 10;
        // 有多少个数准备多少个辅助空间
        int[] help = new int[r - l + 1];
        for (int d = 1; d <= digit; d++) {
            int[] count = new int[radix];
            // count 数组记录对应数字出现的次数，比如个位是 0 的数出现了 n 次，count[0] = n
            for (int i = l; i <= r; i++) {
                int j = getDigit(arr[i], d);
                count[j]++;
            }
            // 将 count 数组 i 位置的值和前一个值相加替换 i 位置的值 ，这样 count 数组的含义就为
            // count[0] 当前位（d 位）<=0 的数字有多少个
            // count[1] 当前位（d 位）<=1 的数字有多少个
            // count[2] 当前位（d 位）<=2 的数字有多少个
            // count[i] 当前位（d 位）<=i 的数字有多少个
            for (int i = 1; i < radix; i++) {
                count[i] = count[i] + count[i - 1];
            }
            // 从右往左遍历数组，提取数字 d 位的数，假如这个数为 5 且 count[5] 位置的数为 6 的话，那就表明个位小于等于 5 的数一共有
            // 6 个，由因为是从右往左遍历的，所以这个数应该被放到 help[5] 这个位置，同时让 count[5] 位置的数减一。
            for (int i = r; i >= l; i--) {
                int j = getDigit(arr[i], d);
                help[count[j] - 1] = arr[i];
                count[j]--;
            }
            for (int i = l, j = 0; i <= r; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    /**
     * 求数组中最大数字十进制位数
     */
    private static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }

    /**
     * 求十进制数制定位上的数，d 代表位数，1 代表个位，2 代表十位，依次类推
     */
    private static int getDigit(int x, int d) {
        return ((x / ((int) Math.pow(10, d - 1))) % 10);
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortTestUtils.generateRandomArray2(maxSize, maxValue);
            int[] arr2 = SortTestUtils.copyArray(arr1);
            int[] arr3 = SortTestUtils.copyArray(arr1);
            radixSort(arr1);
            radixSort2(arr2);
            SortTestUtils.comparator(arr3);
            if (!SortTestUtils.isEqual(arr1, arr2) || !SortTestUtils.isEqual(arr1, arr3)) {
                succeed = false;
                SortTestUtils.printArray(arr1);
                SortTestUtils.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = SortTestUtils.generateRandomArray2(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        radixSort(arr);
        SortTestUtils.printArray(arr);
    }
}
