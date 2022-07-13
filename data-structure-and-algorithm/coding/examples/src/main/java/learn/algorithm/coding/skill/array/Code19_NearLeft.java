package learn.algorithm.coding.skill.array;

/**
 * 在一个有序数组（从小到大）中，找 `>=` 某个数最左侧的位置，即找到 `>=` 某个数的最小值（ceiling）
 */
public class Code19_NearLeft {

    static int nearLeft(int[] array, int num) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int left = 0, right = array.length - 1;
        int index = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] >= num) {
                // index 来到最后一个大于等于 num 的 mid 位置，即题目要求的答案
                index = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] arr = {2, 2, 3 ,4 ,5};
        System.out.println(nearLeft(arr, 2));
    }
}
