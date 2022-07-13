package learn.algorithm.advance.structure.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 假设一个固定大小为 W 的窗口，依次划过 arr，返回每一次滑出状况的最大值。
 * 例如，arr = [4,3,5,4,3,3,6,7]，W = 3，返回：[5,5,5,4,6,7]。
 */
public class Code01_SlidingWindowMaxValue {

    // 暴力方法，每次窗口移动时遍历窗口求最大，时间复杂度 O(N*M)
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

    // 使用双端队列，时间复杂度 O(N) 空间复杂度 O(M)
    static int[] getMax(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w < 1) {
            return null;
        }
        int n = arr.length;
        int[] ans = new int[n - w + 1];
        int left = 0, right = 0;
        // 使用双端队列，头部存放存放窗口内最大值下标
        Deque<Integer> deque = new LinkedList<>();
        while (right < n) {
            int in = arr[right];
            // 这里弹出队列的条件要不要等于都可以
            while (!deque.isEmpty() && in >= arr[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.addLast(right);
            // 扩大窗口
            right++;

            while (right - left == w) {
                int maxIndex = deque.peekFirst();
                ans[left] = arr[maxIndex];
                if (maxIndex == left) {
                    deque.pollFirst();
                }
                // 缩小窗口
                left++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 100;
        int testTimes = 1000000;

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
                break;
            }
        }
        System.out.println("Finish!");
    }
}
