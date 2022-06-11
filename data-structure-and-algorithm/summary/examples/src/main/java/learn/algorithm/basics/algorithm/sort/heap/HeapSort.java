package learn.algorithm.basics.algorithm.sort.heap;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 堆排序（从小到大）
 */
public class HeapSort {

    /**
     * 堆排序
     */
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 将数组变成大根堆结构
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }
        // 将堆顶和堆尾元素交换（即最大元素放到了数组末尾），让除去堆尾元素的部分再成为一个大根堆
        int heapSize = arr.length;
        while (heapSize > 0) {
            swap(arr, 0, --heapSize);
            heapify(arr, 0, heapSize);
        }
    }

    /**
     * 堆排序优化
     */
    public static void heapSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 将数组变成大根堆结构
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        // 将堆顶和堆尾元素交换（即最大元素放到了数组末尾），令除去堆尾元素的部分再成为一个大根堆
        int heapSize = arr.length;
        while (heapSize > 0) {
            swap(arr, 0, --heapSize);
            heapify(arr, 0, heapSize);
        }
    }

    private static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            // 左右两个孩子中，谁大，谁把自己的下标给 largest
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            // 根节点和值最大孩子节点比较，值大的下标给 largest
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            left = index * 2 + 1;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayComparator.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayComparator.copyArray(arr1);
            heapSort2(arr1);
            ArrayComparator.comparator(arr2);
            if (!ArrayComparator.isEqual(arr1, arr2)) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr1);
                ArrayComparator.printArray(arr2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
