package learn.algorithm.coding.skill.array;

/**
 * 在一个有序数组（从小到大），找到 `<=` 某个数的最右侧位置，即找到数组中小于这个数的最大值。
 */
public class Code20_NearRight {

    static int nearRight(int[] array, int num) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int left = 0, right = array.length - 1;
        int index = -1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (array[mid] <= num) {
                // index 来到最后一个小于等于 num 的 mid 位置，即题目要求的答案
                index = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }
}
