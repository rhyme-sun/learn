package learn.algorithm.bisection;

import learn.algorithm.sort.SortTestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 在一个有序数组（从小到大）中，找某个数是否存在，返回满足条件的一个下标，不存在返回 -1。
 */
@Slf4j
public class BSExist {

    static int exist(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) {
            return -1;
        }
        int l = 0;
        int r = sortedArr.length - 1;
        int index = -1;
        while (l <= r) {
            // mid = (l + r) / 2
            // 使用右移运算而不是除法运算，是因为右移运算更快
            // l + ((r - l) >> 1) 而不是 (l + r) >> 1 是为了避免超过 int 的最大值
            int mid = l + ((r - l) >> 1);
            if (sortedArr[mid] == num) {
                return mid;
            } else if (sortedArr[mid] > num) {
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
        log.info("元素 0 在数组中的位置：{}", exist(array, 0));
        log.info("元素 1 在数组中的位置：{}", exist(array, 1));
        log.info("元素 2 在数组中的位置：{}", exist(array, 2));
    }
}
