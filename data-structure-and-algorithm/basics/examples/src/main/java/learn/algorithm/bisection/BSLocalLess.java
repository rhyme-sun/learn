package learn.algorithm.bisection;

import learn.algorithm.sort.SortTestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 局部最少值问题
 */
@Slf4j
public class BSLocalLess {

    public static int localLess(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1; // no exist
        }
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        int l = 1;
        int r = arr.length - 2;
        while (l < r) {
            int mid = l + ((r - l) >> 1);
            if (arr[mid] > arr[mid - 1]) {
                r = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return l;
    }

    public static void main(String[] args) {
        int[] array = SortTestUtils.generateRandomArray(10, 10);
        SortTestUtils.printArray(array);
        log.info("局部最小位置为：{}", localLess(array));
    }
}
