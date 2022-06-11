package learn.algorithm.coding.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

import learn.algorithm.coding.comparator.ArrayComparator;

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
        if (arr == null || arr.length == 0 || w < 1) {
            return null;
        }
        int n = arr.length;
        int[] res = new int[n - w + 1];
        Deque<Integer> maxQ = new LinkedList<>();
        int l = 0, r = 0;
        for (; r < n; r++) {
            // r 入队
            while (!maxQ.isEmpty() && arr[maxQ.peekLast()] <= arr[r]) {
                maxQ.pollLast();
            }
            maxQ.addLast(r);

            if (r > w - 1) {
                // l 出队
                if (maxQ.peekFirst() == l) {
                    maxQ.pollFirst();
                }
                l++;
            }
            res[l] = arr[maxQ.peekFirst()];
        }
        return res;
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
                ArrayComparator.printArray(ans1);
                ArrayComparator.printArray(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
