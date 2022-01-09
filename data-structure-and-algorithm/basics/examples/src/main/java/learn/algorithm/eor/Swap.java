package learn.algorithm.eor;

/**
 * 使用异或运算交换两个数的值，但注意不同交换同一个值。
 *
 * @author ykthree
 * 2021/6/13
 */
public class Swap {

    /**
     * 交换数组 i 和 j 位置的值，不能是同一个位置
     */
    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int[] array = {a, b};

        System.out.println("Before swap.");
        System.out.println("array[0]=" + array[0]);
        System.out.println("array[1]=" + array[1]);
        swap(array, 0, 1);
        System.out.println("After swap.");
        System.out.println("array[0]=" + array[0]);
        System.out.println("array[1]=" + array[1]);
    }
}
