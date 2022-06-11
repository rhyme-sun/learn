package learn.algorithm.basics.algorithm.sort.quick.usage;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 无序数组中的第 K 小
 */
public class Code01_MinKth {

    static int minKth(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k < 1) {
            return -1;
        }
        return process(arr, 0, arr.length - 1, k - 1);
    }

    private static int process(int[] arr, int l, int r, int index) {
        // l == r == index
        if (l >= r) {
            return arr[l];
        }
        // 不止一个数  l +  [0, r -l]
        int pivot = arr[l + (int) (Math.random() * (r - l + 1))];
        int[] range = partition(arr, l, r, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return process(arr, l, range[0] - 1, index);
        } else {
            return process(arr, range[1] + 1, r, index);
        }
    }

    private static int[] partition(int[] arr, int l, int r, int pivot) {
        int less = l - 1;
        int more = r + 1;
        for (int i = l; i < more; ) {
            if (arr[i] < pivot) {
                ArrayComparator.swap(arr, ++less, i++);
            } else if (arr[i] == pivot) {
                i++;
            } else {
                ArrayComparator.swap(arr, --more, i);
            }
        }
        return new int[]{less + 1, more - 1};
    }
}
