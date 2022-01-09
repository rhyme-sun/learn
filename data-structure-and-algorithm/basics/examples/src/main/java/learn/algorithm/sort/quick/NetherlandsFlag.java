package learn.algorithm.sort.quick;

import learn.algorithm.sort.SortTestUtils;

/**
 * 荷兰国旗问题
 *
 * @author ykthree
 * 2021/6/20
 */
public class NetherlandsFlag {

    /**
     * 给定一个数组 arr，和一个整数 num。请把小于 num 的数放在数组的左边，等于的放中间，大于 num 的数放在数组的右边。
     * 实现步骤：
     * 定义小于区的右边界 less，其实位置为 l-1，大于区的左边界 more，起始区为 r。
     * 遍历 [l,r] 范围内数组元素，比较 arr[i] 和 arr[r] 的大小：
     * 若 arr[i] < arr[r]，和小于区边界下一个元素做交换，小于区右边界右移一位，比较下个数；
     * 若 arr[i] = arr[r]，不做处理，比较下个数；
     * 若 arr[i] > arr[r]，和大于区的右边界前一个数交换，大于区左边界左移一位，下标不移动。
     * 最后将 r 位置的数，和大于区左边界的数交换。
     *
     * @return 等于区起始和终止位置
     */
    static int[] netherLandsFlag(int[] arr, int l, int r) {
        if (arr == null || l > r) {
            return new int[]{-1, -1};
        }
        if (l == r) {
            return new int[]{l, r};
        }
        int less = l - 1;
        int more = r;
        for (int i = l; i < more; ) {
            if (arr[i] < arr[r]) {
                swap(arr, ++less, i++);
            } else if (arr[i] == arr[r]) {
                i++;
            } else {
                swap(arr, --more, i);
            }
        }
        swap(arr, more, r);
        return new int[]{less + 1, more};
    }

    /**
     * for test
     * 查找数组 [l,r] 范围内小于 arr[r] 的个数 less，大于 arr[r] 的数的个数 more，则 l+less-1 就是小于区边界位置，r-more+1 就是大于区
     * 边界位置，则 l+less 和 r-more 分别就为等于区的起始和终止位置。
     */
    static int[] comparator(int[] arr, int l, int r) {
        if (arr == null || l > r) {
            return new int[]{-1, -1};
        }
        if (l == r) {
            return new int[]{l, r};
        }
        int less = 0;
        int more = 0;
        for (int i = l; i <= r; i++) {
            if (arr[i] < arr[r]) {
                less++;
            } else if (arr[i] > arr[r]) {
                more++;
            }
        }
        return new int[]{l + less, r - more};
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 10;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortTestUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = SortTestUtils.copyArray(arr1);
            int[] bounds1 = netherLandsFlag(arr1, 0, arr1.length - 1);
            int[] bounds2 = comparator(arr2, 0, arr2.length - 1);
            if (!SortTestUtils.isEqual(bounds1, bounds2)) {
                succeed = false;
                SortTestUtils.printArray(arr1);
                SortTestUtils.printArray(arr2);
                SortTestUtils.printArray(bounds1);
                SortTestUtils.printArray(bounds2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");
        int[] arr = SortTestUtils.generateRandomArray(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        int[] bounds = netherLandsFlag(arr, 0, arr.length - 1);
        SortTestUtils.printArray(arr);
        SortTestUtils.printArray(bounds);
    }
}
