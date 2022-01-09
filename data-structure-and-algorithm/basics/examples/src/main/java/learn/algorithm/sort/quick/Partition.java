package learn.algorithm.sort.quick;

import learn.algorithm.sort.SortTestUtils;

/**
 * 分区问题
 *
 * @author ykthree
 * 2021/6/20
 */
public class Partition {

    /**
     * 对数组 arr 进行分区，使得 [l,r] 范围内，小于等于 arr[r] 的数再左边，大于 arr[r] 的数在右边。返回分界位置。
     * 实现步骤：
     * 定义小于等于区的右边界（less），起始位置为 l-1；
     * 遍历 [l,r-1]，比较 arr[i] 和 arr[r] 的大小：
     * 若 arr[i] <= arr[r]，和右边界下一个位置做交换，并将右边界右移一位，比较下个数；
     * 若 arr[i] > arr[r]，不做处理，比较下个数。
     * 最后将 r 位置的数和右边界下一个位置做交换，返回 less。
     *
     * @return 分界位置
     */
    static int partition(int[] arr, int l, int r) {
        if (arr == null || l > r) {
            return -1;
        }
        if (l == r) {
            return l;
        }
        int less = l - 1;
        for (int i = l; i < r; i++) {
            if (arr[i] <= arr[r]) {
                swap(arr, ++less, i);
            }
        }
        swap(arr, ++less, r);
        return less;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * for test
     * 查找数组 [l,r] 范围内小于等于 arr[r] 的个数 less，则 l+less-1 就是小于等于边界位置。
     */
    static int comparator(int[] arr, int l, int r) {
        if (arr == null || l > r) {
            return -1;
        }
        if (l == r) {
            return l;
        }
        int less = 0;
        for (int i = l; i <= r; i++) {
            if (arr[i] <= arr[r]) {
                less++;
            }
        }
        return l + less - 1;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 10;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortTestUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = SortTestUtils.copyArray(arr1);
            int bounds1 = partition(arr1, 0, arr1.length - 1);
            int bounds2 = comparator(arr2, 0, arr2.length - 1);
            if (bounds1 != bounds2) {
                succeed = false;
                SortTestUtils.printArray(arr1);
                SortTestUtils.printArray(arr2);
                System.out.println(bounds1);
                System.out.println(bounds2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");
        int[] arr = SortTestUtils.generateRandomArray(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        int bounds = partition(arr, 0, arr.length - 1);
        SortTestUtils.printArray(arr);
        System.out.println(bounds);
    }
}
