package learn.algorithm.coding.practice.p01;

import java.util.Arrays;

/**
 * 题目描述如下：
 * 一个数组中只有两种字符 'G' 和 ’B’，可以让所有的 G 都放在左侧，所有的 B 都放在右侧或者可以让所有的 G 都放在右侧，
 * 所有的 B 都放在左侧，但是只能在相邻字符之间进行交换操作，返回至少需要交换几次。
 */
public class Code04_MinSwapStep {

    static int minSwapStep(char[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        // 记录字符应该放的位置
        int index = 0;
        int swapTimes1 = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 'G') {
                swapTimes1 += i - index;
                index++;
            }
        }
        index = 0;
        int swapTimes2 = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 'B') {
                swapTimes2 += i - index;
                index++;
            }
        }
        return Math.min(swapTimes1, swapTimes2);
    }

    /**
     * 使用冒泡排序，分别进行正序和逆序排序，返回两次排序中交换次数较少的那个
     */
    static int minSwapStep1(char[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        char[] copyArr = Arrays.copyOf(arr, n);
        // 0-n-1
        // 0-n-2
        // ...
        // 0-1
        int swapSteps1 = 0;
        for (int i = n - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapSteps1++;
                }
            }
        }

        int swapSteps2 = 0;
        for (int i = n - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (copyArr[j] < copyArr[j + 1]) {
                    swap(copyArr, j, j + 1);
                    swapSteps2++;
                }
            }
        }
        return Math.min(swapSteps1, swapSteps2);
    }

    /**
     * 使用插入排序，，分别进行正序和逆序排序，返回两次排序中交换次数较少的那个
     */
    static int minSwapStep2(char[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        char[] copyArr = Arrays.copyOf(arr, n);
        // 0-1
        // 0-2
        // ...
        // 0-n-1
        int swapSteps1 = 0;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j - 1, j);
                    swapSteps1++;
                }
            }
        }

        int swapSteps2 = 0;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (copyArr[j] > copyArr[j - 1]) {
                    swap(copyArr, j - 1, j);
                    swapSteps2++;
                }
            }
        }
        return Math.min(swapSteps1, swapSteps2);
    }

    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        char[] arr = new char[]{'G', 'G', 'B', 'B', 'G', 'G', 'B'};
        char[] arr1 = Arrays.copyOf(arr, arr.length);
        char[] arr2 = Arrays.copyOf(arr, arr.length);
        int ans1 = minSwapStep1(arr1);
        int ans2 = minSwapStep2(arr2);
        int ans3 = minSwapStep(arr);
        System.out.println(ans1);
        System.out.println(ans2);
        System.out.println(ans3);
    }
}
