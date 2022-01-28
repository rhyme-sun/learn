package learn.algorithm.bisection;

import learn.algorithm.sort.SortTestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 在一个有序数组（从小到大）中，找 >= 某个数最左侧的位置（大于或等于某个数的最小值）。
 */
@Slf4j
public class BSNearLeft {

    static int nearLeft(int[] array, int num) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int l = 0;
        int r = array.length - 1;
        int index = -1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (array[mid] >= num) {
                index = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] array = SortTestUtils.generateRandomArray(10, 10);
        SortTestUtils.comparator(array);
        SortTestUtils.printArray(array);
        log.info("数组中大于 0 的最左位置为：{}", nearLeft(array, 0));
    }
}
