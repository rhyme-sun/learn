package learn.algorithm.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 假设一个固定大小为 W 的窗口，依次划过 arr，返回每一次滑出状况的最大值。
 * 例如，arr = [4,3,5,4,3,3,6,7]，W = 3，返回：[5,5,5,4,6,7]。
 */
public class SlidingWindowMaxArray {

    /**
     * 暴力方法，暴力记录每次窗口移动时的最大值
     *
     * @param arr 样本数组
     * @param w   滑动窗口的大小
     * @return 结果数组，数组 i 位置的值表示窗口 i+1 次移动时，内部最大元素
     */
    static int[] right(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int n = arr.length;
        int[] res = new int[n - w + 1];
        int index = 0;
        int l = 0;
        int r = w - 1;
        while (r < n) {
            int max = arr[l];
            // 一个窗口内最大值
            for (int i = l + 1; i <= r; i++) {
                max = Math.max(max, arr[i]);
            }
            res[index++] = max;
            l++;
            r++;
        }
        return res;
    }

    /**
     * 使用双端队列
     *
     * @param arr 样本数组
     * @param w   滑动窗口的大小
     * @return 结果数组，数组 i 位置的值表示窗口 i+1 次移动时，内部最大元素
     */
    static int[] getMax(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int[] res = new int[arr.length - w + 1];
        // 双端队列
        Deque<Integer> queue = new LinkedList<>();
        // 窗口向右边移动（遍历 r 运动轨迹），每移动一次，都有一个元素进入窗口，有一个元素从从窗口移出
        int index = 0;
        for (int r = 0; r < arr.length; r++) {
            addLast(arr, r, queue);
            pollFirst(r-w, queue);
            if (r >= w - 1) {
                res[index++] = arr[queue.peekFirst()];
            }
        }
        return res;
    }

    /**
     * 向双端队列队尾添加元素，并保证队列头部为当前窗口最大值
     *
     * @param arr   样本数组
     * @param r     窗口 r 位置（数组上）
     * @param queue 双端队列
     */
    static void addLast(int[] arr, int r, Deque<Integer> queue) {
        // 从队列尾部弹出元素直到队列尾部元素大于 arr[r] 的值或者队列为空
        while (!queue.isEmpty() && arr[queue.peekLast()] < arr[r]) {
            queue.pollLast();
        }
        queue.addLast(r);
    }

    /**
     * 移除双端队列头部元素，并保证队列头部为当前窗口最大值
     *
     * @param l     窗口 l 位置（数组上）
     * @param queue 双端队列
     */
    static void pollFirst(int l, Deque<Integer> queue) {
        if (queue.peekFirst() == l) {
            queue.pollFirst();
        }
    }

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 100;
        int testTimes = 100000;

        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = right(arr, w);
            int[] ans2 = getMax(arr, w);
            if (!ArrayComparator.isEqual(ans1, ans2)) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println("暴力迭代：");
                ArrayComparator.printArray(ans1);
                System.out.println("双端队列：");
                ArrayComparator.printArray(ans2);
            }
        }
        System.out.println("Finish!");
    }
}
