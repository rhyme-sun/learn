package learn.algorithm.coding.skill.array;

/**
 * 在一个有序数组（从小到大）中，找某个数是否存在，这时候我们就可以使用二分法实现，代码如下所示：
 */
public class Code18_BSExist {

    static int exist(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int left = 0, right = arr.length - 1;
        int index = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == num) {
                return mid;
            } else if (arr[mid] > num) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }
}
