package learn.algorithm.sort;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * 排序算法测试工具类
 */
@Slf4j
public class SortTestUtils {

    /**
     * 使用 JDK 自带排序方法，作为对数器中验证自定义算法方法 a 是否正确的方法 b。
     */
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        return Arrays.copyOf(arr, arr.length);
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        return Arrays.equals(arr1, arr2);
    }

    public static void printArray(int[] arr) {
        log.info(Arrays.toString(arr));
    }
}
