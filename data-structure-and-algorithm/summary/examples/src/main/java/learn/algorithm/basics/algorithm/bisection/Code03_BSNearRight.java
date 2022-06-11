package learn.algorithm.basics.algorithm.bisection;


import learn.algorithm.comparator.ArrayComparator;

/**
 * 在一个有序数组（从小到大）中，找 <= 某个数最右侧的位置（小于或等于某个数的最大值）
 */
public class Code03_BSNearRight {

    static int nearRight(int[] array, int num) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int l = 0;
        int r = array.length - 1;
        int index = -1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            // index 来到最后一个小于等于 num 的 mid 位置
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
        int[] array = ArrayComparator.generateRandomArray(10, 10);
        // 升序排序
        ArrayComparator.comparator(array);
        ArrayComparator.printArray(array);
        System.out.println(nearRight(array, 0));
    }
}
