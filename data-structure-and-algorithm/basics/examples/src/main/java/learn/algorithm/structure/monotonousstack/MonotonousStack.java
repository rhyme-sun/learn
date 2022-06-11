package learn.algorithm.structure.monotonousstack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 使用单调栈找到数组元素最近最小的元素
 */
public class MonotonousStack {

    /**
     * 求数组每个元素左右两侧距离小于且距离其最近的元素位置（数组中无重复元素）
     * 位置信息用一个二维数组来表示：
     * pos[i][0] 表示左侧离 i 最近且小于 i 为位置元素的下标
     * pos[i][1] 表示右侧侧离 i 最近且小于 i 为位置元素的下标
     *
     * @return 位置信息
     */
    static int[][] nearLessNoRepeat(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[][]{};
        }
        int n = arr.length;
        int[][] ans = new int[n][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            // i 入栈
            while (!stack.isEmpty() && arr[i] < arr[stack.peek()]) {
                int pop = stack.pop();
                ans[pop][0] = stack.isEmpty() ? -1 : stack.peek();
                ans[pop][1] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int pop = stack.pop();
            ans[pop][0] = stack.isEmpty() ? -1 : stack.peek();
            ans[pop][1] = -1;
        }
        return ans;
    }

    /**
     * 使用数组替代系统栈
     */
    static int[][] nearLessNoRepeat2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[][]{};
        }
        int n = arr.length;
        int[][] ans = new int[n][2];

        int top = -1;  // 记录当前栈顶位置
        int size = 0;  // 栈中元素个数
        int[] stack = new int[n];

        for (int i = 0; i < n; i++) {
            // i 入栈
            while (size != 0 && arr[i] < arr[stack[top]]) {
                int pop = stack[top--];
                size--;
                ans[pop][0] = size == 0 ? -1 : stack[top];
                ans[pop][1] = i;
            }
            stack[++top] = i;
            size++;
        }
        while (size != 0) {
            int pop = stack[top--];
            size--;
            ans[pop][0] = size == 0 ? -1 : stack[top];
            ans[pop][1] = -1;
        }
        return ans;
    }

    static int[][] nearLess(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[][]{};
        }
        int n = arr.length;
        int[][] ans = new int[n][2];
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[i] < arr[stack.peek().get(0)]) {
                List<Integer> pops = stack.pop();
                for (Integer pop : pops) {
                    ans[pop][0] = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                    ans[pop][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[i] == arr[stack.peek().get(0)]) {
                stack.peek().add(i);
            } else {
                List<Integer> push = new ArrayList<>();
                push.add(i);
                stack.push(push);
            }
        }
        while (!stack.isEmpty()) {
            List<Integer> pops = stack.pop();
            for (Integer pop : pops) {
                ans[pop][0] = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                ans[pop][1] = -1;
            }
        }
        return ans;
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
        int testTimes = 2000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = ArrayComparator.generateNoRepeatRandomArray(maxSize);
            final int[][] ans11 = nearLessNoRepeat2(arr1);
            final int[][] ans12 = rightWay(arr1);
            if (!ArrayComparator.isEqual(ans11, ans12)) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr1);
                ArrayComparator.printArray(ans11);
                ArrayComparator.printArray(ans12);
                break;
            }

            //int[] arr2 = ArrayComparator.generateRandomArray(maxSize, maxValue);
            int[] arr2 = {-4, -5, 1, 0, 14, 0, 4};
            final int[][] ans21 = nearLess(arr2);
            final int[][] ans22 = rightWay(arr2);
            if (!ArrayComparator.isEqual(ans21, ans22)) {
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
