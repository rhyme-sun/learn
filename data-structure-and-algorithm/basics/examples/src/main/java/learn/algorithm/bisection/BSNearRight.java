package learn.algorithm.bisection;

import learn.algorithm.sort.SortTestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 在一个有序数组（从小到大）中，找 <= 某个数最右侧的位置（小于或等于某个数的最大值）
 */
@Slf4j
public class BSNearRight {

    static int nearRight(int[] array, int num) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int l = 0;
        int r = array.length - 1;
        int index = -1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (array[mid] <= num) {
                index = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        // 生成一个随机数组
        int[] array = SortTestUtils.generateRandomArray(10, 10);
        // 升序排序
        SortTestUtils.comparator(array);
        SortTestUtils.printArray(array);
        log.info("数组中大小于 0 最右侧的位置为：{}", nearRight(array, 0));
    }
}
