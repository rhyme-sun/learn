package learn.algorithm.basics.algorithm.bit.xor;


import learn.algorithm.comparator.ArrayComparator;

/**
 * 使用异或运算交换两个数的值，不需要额外的空间。
 */
public class Code01_SwapWithoutExtraSpace {

    /**
     * 交换数组 i 和 j 位置的值，不能是同一个位置
     */
    static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void main(String[] args) {
        int a = 1;
        int b = 1;
        int[] array = {a, b};
        ArrayComparator.printArray(array);
        swap(array, 0, 1);
        ArrayComparator.printArray(array);
    }
}
