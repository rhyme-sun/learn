package learn.algorithm.topk;

import java.util.Comparator;
import java.util.PriorityQueue;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 求一个无序数组第 K 小的数
 */
public class FindMinKth {

    /**
     * 方法 1，利用大根堆 时间复杂度 O(N*logK)
     */
    static int minKth1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.add(arr[0]);
        for (int i = 1; i < k; i++) {
            maxHeap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    /**
     * 使用改写的快排，，时间复杂度 O(N)，k >= 1
     */
    static int minKth2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        return process(ArrayComparator.copyArray(arr), 0, arr.length - 1, k - 1);
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

    /**
     * 分区问题（荷兰国旗分区）
     *
     * @param arr   分区数组
     * @param l     需要分区区域的左边界
     * @param r     需要分区区域的右边界
     * @param pivot 随机在 l~r 上选择的数，作为分区的基准值
     * @return 等于区左右边界
     */
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

    /**
     * 使用 BFPRT 算法，时间复杂度为 O(N)
     */
    static int minKth3(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        return bfprt(ArrayComparator.copyArray(arr), 0, arr.length - 1, k - 1);
    }

    private static int bfprt(int[] arr, int l, int r, int index) {
        if (l >= r) {
            return arr[l];
        }
        int pivot = medianOfMedians(arr, l, r);
        int[] range = partition(arr, l, r, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfprt(arr, l, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, r, index);
        }
    }

    /**
     * 5 个数一组，分成 N/5 组，每组拿出中间值组成一个数组，再在这个数组中选出中间值作为划分值
     *
     * @return 中位数，作为分区时的划分值
     */
    private static int medianOfMedians(int[] arr, int l, int r) {
        int size = r - l + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = l + team * 5;
            mArr[team] = getMedian(arr, teamFirst, Math.min(r, teamFirst + 4));
        }
        // 找出 mArr 的中位数
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    /**
     * 排序并返回中间值
     */
    private static int getMedian(int[] arr, int l, int r) {
        // 使用插入排序
        insertionSort(arr, l, r);
        return arr[(l + r) / 2];
    }

    private static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                ArrayComparator.swap(arr, j, j + 1);
            }
        }
    }

    public static void main(String[] args) {
        int testTimes = 1000000;
        int maxSize = 10;
        int maxValue = 100;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxSize, maxValue);

//            int k = (int) (Math.random() * arr.length) + 1;
            int k = 0;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("Finish!");
    }
}
