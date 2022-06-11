package learn.algorithm.advance.structure.monotonousstack;

import java.util.Stack;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 给定一个**只包含正数**的数组 arr，arr 中任何一个子数组 sub，一定都可以算出（sub 累加和）*（sub 中的最小值），
 * 那么所有子数组中这个值最大是多少？
 */
public class Code02_AllTimesMinToMax {

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

    /**
     * 使用单调栈
     */
    static int max2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int size = arr.length;
        // 前缀和数组，sum[i] 表示 i 和 i 之前元素的累加和，比如 i 到 j 位置的子数组的累加和就等于 sum[j] - sum[i-1]
        int[] sums = new int[size];
        sums[0] = arr[0];
        for (int i = 1; i < size; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }
        int max = Integer.MIN_VALUE;
        // 这里没有使用链表，是因为相等的元素在后续的处理下可以得到正确的答案
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            // 这里在 arr[i] 和栈顶元素相等时直接弹出，但所得的 max 可能不是正确的最大值，但后序压入栈的相等的值最终可以得到最优答案
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
