package learn.algorithm.structure.monotonousstack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import learn.algorithm.comparator.ArrayComparator;

public class AllTimesMinToMax {

    /**
     * 暴力方法，穷举所有情况，找到最大值
     */
    static int max1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                max = Math.max(max, minNum * sum);
            }
        }
        return max;
    }


    static int max2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 大到小
        Stack<List<Integer>> stack = new Stack<>();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            // 不能入栈
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                int popValue = arr[stack.pop().get(0)];

                int rightLessIndex = i;
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);

                int sum = 0;
                for (int j = leftLessIndex + 1; j <= rightLessIndex - 1; j++) {
                    sum += arr[j];
                }
                max = Math.max(max, sum * popValue);
            }
            // 可以入栈
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(i);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }
        while (!stack.isEmpty()) {
            int popValue = arr[stack.pop().get(0)];
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            int sum = 0;
            for (int j = leftLessIndex + 1; j <= arr.length - 1; j++) {
                sum += arr[j];
            }
            max = Math.max(max, sum * popValue);
        }
        return max;
    }

    /**
     * 使用单调栈
     */
    static int max3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int size = arr.length;
        // 前缀和数组，可以方便的得到任意一个子数组的累加和，比如 i 到 j 位置的子数组的累加和就等于 sum[j] - sum[i-1]
        int[] sums = new int[size];
        sums[0] = arr[0];
        for (int i = 1; i < size; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }
        int max = Integer.MIN_VALUE;
        // 这里没有使用链表，是因为相等的元素在后续的处理下可以得到正确的答案
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            // 这里在 arr[i] 和栈顶元素相等时直接弹出，但所得的 max 可能不是正确的最大值
            // 但后序压入栈的相等的值最终可以得到最优答案，我们又知道这联通的两个相等的值具有相同的最近最小边界
            // 所以前面值算小了没关系，后面相等的值可以得到最优答案去参与比较（不严格纠结每个位置得到最优的答案，只要保证最终答案正确即可）
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int j = stack.pop();
                max = Math.max(max, (stack.isEmpty() ? sums[i - 1] : (sums[i - 1] - sums[stack.peek()])) * arr[j]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            max = Math.max(max, (stack.isEmpty() ? sums[size - 1] : (sums[size - 1] - sums[stack.peek()])) * arr[j]);
        }
        return max;
    }

    public static void main(String[] args) {
        int testTimes = 2000000;
        int maxSize = 30;
        int maxValue = 100;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generatePositiveRandomArray(maxSize, maxValue);
            if (max1(arr) != max2(arr)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("Finish!");
    }
}
