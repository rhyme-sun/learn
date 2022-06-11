package learn.algorithm.basics.algorithm.bisection;


import learn.algorithm.comparator.ArrayComparator;

/**
 * 在一个有序数组（从小到大）中，找 >= 某个数最左侧的位置（大于或等于某个数的最小值）。
 */
public class Code02_BSNearLeft {

    static int nearLeft(int[] array, int num) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int l = 0;
        int r = array.length - 1;
        int index = -1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            // index 来到最后一个大于等于 num 的 mid 位置
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
        int[] array = ArrayComparator.generateRandomArray(10, 10);
        ArrayComparator.comparator(array);
        ArrayComparator.printArray(array);
        System.out.println(nearLeft(array, 0));
    }
}
