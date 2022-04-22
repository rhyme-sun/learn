package learn.algorithm.comparator;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * 数组对数器
 */
@Slf4j
public class ArrayComparator {

    /**
     * 使用 JDK 自带排序方法，作为验证排序算法正确性的辅助方法
     */
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    /**
     * 交换数组两个位置的元素
     */
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 生成随机数组，值为 int，可为负数
     *
     * @param maxSize  数组最大长度
     * @param maxValue 数组元素值最大大小
     * @return 随机数组
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    /**
     * 生成随机数组，值为 int，值为正数
     *
     * @param maxSize  数组最大长度
     * @param maxValue 数组元素值最大大小
     * @return 值为正数的随机数组
     */
    public static int[] generatePositiveRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue) * Math.random() + 1);
        }
        return arr;
    }

    /**
     * 拷贝数组
     */
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        return Arrays.copyOf(arr, arr.length);
    }

    /**
     * 判断两个数组是否相等
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        return Arrays.equals(arr1, arr2);
    }

    /**
     * 打印数组
     */
    public static void printArray(int[] arr) {
        log.info(Arrays.toString(arr));
    }
}