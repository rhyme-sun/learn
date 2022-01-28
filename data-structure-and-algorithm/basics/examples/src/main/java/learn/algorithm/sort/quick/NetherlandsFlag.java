package learn.algorithm.sort.quick;

import learn.algorithm.sort.SortTestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 荷兰国旗问题
 */
@Slf4j
public class NetherlandsFlag {

    /**
     * 给定一个数组 arr，在 [l,r] 范围内，选择 arr[r] 作为分界数，将数组分成三个区，使得小于 arr[r] 的数在左边，等于 arr[r] 的数在中间，
     * 大于 arr[r] 的数在右边，最后返回等于区的左右边界，步骤为：
     * 定义等于区的左边界（记为 less）的起始位置为 l-1，定义右边界（记为 more）的起始位置为 r；
     * 遍历 [l,r-1]，比较 arr[i] 和 arr[r] 的大小；
     * 若 arr[i] < arr[r]，将 arr[i] 和 less 的下一个位置做交换，并将 less 右移一位；
     * 若 arr[i] = arr[r]，不做处理，比较下一个数；
     * 若 arr[i] > arr[r]，将 arr[i] 和 more 前一个位置的数做交换，并且将 more 左移一位，光标不移动（即不执行 i++）；
     * 最后将 arr[r] 和 more 位置的数做交换，此时的 less 和 more 就是等于区的左右边界，且分区完毕。
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
                SortTestUtils.swap(arr, ++less, i++);
            } else if (arr[i] == arr[r]) {
                i++;
            } else {
                SortTestUtils.swap(arr, --more, i);
            }
        }
        SortTestUtils.swap(arr, more, r);
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
        log.info(succeed ? "Nice!" : "Oops!");
        int[] arr = SortTestUtils.generateRandomArray(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        int[] bounds = netherLandsFlag(arr, 0, arr.length - 1);
        SortTestUtils.printArray(arr);
        SortTestUtils.printArray(bounds);
    }
}
