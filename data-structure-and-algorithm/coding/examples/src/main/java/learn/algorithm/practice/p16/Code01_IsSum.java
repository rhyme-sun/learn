package learn.algorithm.practice.p16;

import java.util.HashSet;
import java.util.Set;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 给定一个有正、有负、有 0 的数组 arr，给定一个整数 k，返回 arr 的子序列是否能累加出 k。
 * <p>
 * - 正常怎么做？
 * - 如果 arr 中的数值很大，但是 arr 的长度不大，怎么做？
 */
public class Code01_IsSum {

    static boolean isSumK(int[] arr, int sum) {
        if (arr == null || arr.length == 0) {
            return sum == 0;
        }
        return process(arr, 0, sum);
    }

    // 来到 index 位置，距离累加和还剩 rest，返回后序数字是否能消耗完 rest
    private static boolean process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0;
        }
        if (rest == 0) {
            return true;
        }
        return process(arr, index + 1, rest) || process(arr, index + 1, rest - arr[index]);
    }

    // 严格递推优化
    static boolean isSumK2(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        int min = 0;
        int max = 0;
        for (int num : arr) {
            min += num < 0 ? num : 0;
            max += num > 0 ? num : 0;
        }
        if (sum < min || sum > max) {
            return false;
        }

        int n = arr.length;
        int m = max - min;
        // rest 范围 [sum-max,sum-min]
        // sum-max  rest  sum-min
        // 0        1      2
        // sum-max      rest     sum-min
        // dp[i][j] 表示在 0~i 范围内自由选择，距离目标累加和恰是否好还剩 rest 其中 rest-sum+max=j
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[n][max - sum] = true;
        for (int i = n - 1; i >= 0; i--) {
            for (int rest = sum - max; rest <= sum - min; rest++) {
                // rest 对应的实际下标
                int j = rest - sum + max;
                dp[i][j] = dp[i + 1][j];
                if (j - arr[i] >= 0 && j - arr[i] <= m) {
                    dp[i][j] = dp[i][j] || dp[i + 1][j - arr[i]];
                }
            }
        }
        // 0 sum   sum 的实际位置为：sum-sum+max
        return dp[0][max];
    }

    // 分治的方法
    // 如果 arr 中的数值特别大，动态规划方法依然会很慢
    // 此时如果 arr 的数字个数不算多（40 以内，2^20=1,000,000），哪怕其中的数值很大，分治的方法也将是最优解
    public static boolean isSum4(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        // 保证左右部分都有数
        if (arr.length == 1) {
            return arr[0] == sum;
        }
        int n = arr.length;
        int mid = n >> 1;
        Set<Integer> leftSum = new HashSet<>();
        Set<Integer> rightSum = new HashSet<>();
        // 0...mid-1
        process(arr, 0, mid, 0, leftSum);
        // mid..n-1
        process(arr, mid, n, 0, rightSum);
        // 单独查看，只使用左部分，能不能搞出 sum
        // 单独查看，只使用右部分，能不能搞出 sum
        // 下面这个 if 可以省略，为了方便理解这里列出来
        if (leftSum.contains(sum) || rightSum.contains(sum)) {
            return true;
        }
        // 左 + 右，联合能不能搞出 sum
        for (int l : leftSum) {
            if (rightSum.contains(sum - l)) {
                return true;
            }
        }
        return false;
    }

    // arr[0...i-1]决定已经做完了！形成的累加和是pre
    // arr[i...end - 1] end(终止)  所有数字随意选择，
    // arr[0...end-1]所有可能的累加和存到ans里去
    private static void process(int[] arr, int i, int end, int pre, Set<Integer> ans) {
        if (i == end) {
            ans.add(pre);
        } else {
            process(arr, i + 1, end, pre, ans);
            process(arr, i + 1, end, pre + arr[i], ans);
        }
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int maxSize = 20;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxSize, maxValue);
            int sum = (int) (Math.random() * ((maxValue << 1) + 1)) - maxValue;
            boolean ans1 = isSumK(arr, sum);
            boolean ans2 = isSumK2(arr, sum);
            boolean ans3 = isSum4(arr, sum);
            if (ans1 ^ ans2 || ans2 ^ ans3) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println("sum : " + sum);

                System.out.println("方法一答案 : " + ans1);
                System.out.println("方法二答案 : " + ans2);
                System.out.println("方法三答案 : " + ans3);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
