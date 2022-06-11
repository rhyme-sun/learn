package learn.algorithm.basics.algorithm.bisection;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 局部最小值问题：对于一个长度为 N 的无序且相邻不等的数组，对于首元素，若第 1 个元素比第 2 个元素小，第 1 个元素就是局部最小值；对末尾元素，
 * 若第 N-1 个元素比第 N 个元素大，第 N 元素就是局部最小值；对于中间的元素，若与其相邻元素都比它大，则该元素为局部最小值。
 */
public class Code02_BSLocalLess {

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
        int[] array = ArrayComparator.generateRandomArray(10, 10);
        ArrayComparator.printArray(array);
        System.out.println(localLess(array));
    }
}
