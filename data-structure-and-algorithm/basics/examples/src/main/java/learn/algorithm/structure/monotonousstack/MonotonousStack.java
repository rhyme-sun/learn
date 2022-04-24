package learn.algorithm.structure.monotonousstack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 使用单调栈找到数组元素最近最小的元素
 */
public class MonotonousStack {

    /**
     * 没有重复元素
     *
     * @param arr 样本数组
     * @return 数组元素左侧和右侧最近最小值，ans[i][0] 表示样本数组 i 位置左侧最近最小值；ans[i][1] 表示样本数组 i 位置右侧最近最小值
     */
    static int[][] getNearLessNoRepeat(int[] arr) {
        int[][] res = new int[arr.length][2];
        // 栈中存放元素下标
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int j = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
                res[j][0] = leftLessIndex;
                res[j][1] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
            res[j][0] = leftLessIndex;
            res[j][1] = -1;
        }
        return res;
    }

    /**
     * 存在重复元素
     *
     * @param arr 样本数组
     * @return 数组元素左侧和右侧最近最小值，ans[i][0] 表示样本数组 i 位置左侧最近最小值；ans[i][1] 表示样本数组 i 位置右侧最近最小值
     */
    static int[][] getNearLess(int[] arr) {
        int[][] res = new int[arr.length][2];
        // 栈中存放下标链表
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> popIs = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer popI : popIs) {
                    res[popI][0] = leftLessIndex;
                    res[popI][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(Integer.valueOf(i));
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }
        while (!stack.isEmpty()) {
            List<Integer> popIs = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer popi : popIs) {
                res[popi][0] = leftLessIndex;
                res[popi][1] = -1;
            }
        }
        return res;
    }

    /**
     * for test
     * 暴力方法求元素最近最小
     */
    static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    public static void main(String[] args) {
        int maxSize = 10;
        int maxValue = 20;
        int testTimes = 2000000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = ArrayComparator.generateNoRepeatRandomArray(maxSize);
            final int[][] ans11 = getNearLessNoRepeat(arr1);
            final int[][] ans12 = rightWay(arr1);
            if (!ArrayComparator.isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr1);
                ArrayComparator.printArray(ans11);
                ArrayComparator.printArray(ans12);
                break;
            }

            int[] arr2 = ArrayComparator.generateRandomArray(maxSize, maxValue);
            final int[][] ans21 = getNearLess(arr2);
            final int[][] ans22 = rightWay(arr2);
            if (!ArrayComparator.isEqual(getNearLess(arr2), rightWay(arr2))) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr2);
                ArrayComparator.printArray(ans21);
                ArrayComparator.printArray(ans22);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
