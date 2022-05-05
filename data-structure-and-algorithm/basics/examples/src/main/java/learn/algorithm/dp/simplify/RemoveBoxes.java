package learn.algorithm.dp.simplify;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给出一些不同颜色的盒子 boxes ，盒子的颜色由不同的正数表示。
 * <p>
 * 你将经过若干轮操作去去掉盒子，直到所有的盒子都去掉为止。每一轮你可以移除具有相同颜色的连续 k 个盒子（k >= 1），
 * 这样一轮之后你将得到 k * k 个积分。返回你能获得的最大积分和。
 * <p>
 * 比如：
 * 输入：boxes = [1,3,2,2,2,3,4,3,1]
 * 输出：23
 * 解释：
 * [1, 3, 2, 2, 2, 3, 4, 3, 1]
 * ----> [1, 3, 3, 4, 3, 1] (3*3=9 分)
 * ----> [1, 3, 3, 3, 1] (1*1=1 分)
 * ----> [1, 1] (3*3=9 分)
 * ----> [] (2*2=4 分)
 */
public class RemoveBoxes {

    static int removeBoxes(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        return process(boxes, 0, boxes.length - 1, 0);
    }

    /**
     * 考虑在 l~r 范围内，前面有 k 个 arr[l]，在这种情况下讨论消除箱子的可能性，返回最大积分
     *
     * @param l l
     * @param r l
     * @param k k 个 arr[l]
     * @return 最大积分
     */
    private static int process(int[] boxes, int l, int r, int k) {
        if (l > r) {
            return 0;
        }
        // 可能性 1：arr[l] 位置和前 k 个 arr[l] 一起消除
        int p1 = (k + 1) * (k + 1) + process(boxes, l + 1, r, 0);
        // 可能性 2：arr[l] 和后面等于 arr[l] 的箱子一起消除
        int p2 = 0;
        for (int i = l + 1; i <= r; i++) {
            if (boxes[i] == boxes[l]) {
                // l+1~i 消除
                int pre = process(boxes, l + 1, i - 1, 0);
                int next = process(boxes, i, r, k + 1);
                p2 = Math.max(p2, pre + next);
            }
        }
        return Math.max(p1, p2);
    }

    /**
     * 记忆化搜索优化
     */
    static int removeBoxes2(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        return process2(boxes, 0, n - 1, 0, dp);
    }


    /**
     * 考虑在 l~r 范围内，前面有 k 个 arr[l]，在这种情况下讨论消除箱子的可能性，返回最大积分
     *
     * @param l l
     * @param r l
     * @param k k 个 arr[l]
     * @return 最大积分
     */
    private static int process2(int[] boxes, int l, int r, int k, int[][][] dp) {
        if (l > r) {
            return 0;
        }
        if (dp[l][r][k] != 0) {
            return dp[l][r][k];
        }
        // 可能性 1：arr[l] 位置和前 k 个 arr[l] 一起消除
        int p1 = (k + 1) * (k + 1) + process2(boxes, l + 1, r, 0, dp);
        // 可能性 2：arr[l] 和后面等于 arr[l] 的箱子一起消除
        int p2 = 0;
        for (int i = l + 1; i <= r; i++) {
            if (boxes[i] == boxes[l]) {
                // l+1~i-1 消除
                int pre = process2(boxes, l + 1, i - 1, 0, dp);
                int next = process2(boxes, i, r, k + 1, dp);
                p2 = Math.max(p2, pre + next);
            }
        }
        int ans = Math.max(p1, p2);
        dp[l][r][k] = ans;
        return ans;
    }

    /**
     * 记忆化搜索优化，优化常数项时间
     */
    static int removeBoxes3(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        return process3(boxes, 0, n - 1, 0, dp);
    }

    /**
     * 考虑在 l~r 范围内，前面有 k 个 arr[l]，在这种情况下讨论消除箱子的可能性，返回最大积分
     *
     * @param l l
     * @param r l
     * @param k k 个 arr[l]
     * @return 最大积分
     */
    private static int process3(int[] boxes, int l, int r, int k, int[][][] dp) {
        if (l > r) {
            return 0;
        }
        if (dp[l][r][k] != 0) {
            return dp[l][r][k];
        }
        // 可能性 1：arr[l] 位置和前 k 个 arr[l] 一起消除
        // 常数项时间优化，如果 l 后面紧接着和 arr[l] 相同的箱子，扩充 k 的值
        int last = l + 1;
        // last 来到第一个不为 arr[l] 的位置
        while (last <= r && boxes[last] == boxes[l]) {
            last++;
        }
        int preK = k + last - l;
        int p1 = preK * preK + process3(boxes, last, r, 0, dp);
        // 可能性 2：arr[l] 和后面等于 arr[l] 的箱子一起消除
        int p2 = 0;
        for (int i = last + 1; i <= r; i++) {
            // 后面连着一块的，只考虑第一个，因为递归调用时，会扩充 k
            if (boxes[i] == boxes[l] && boxes[i - 1] != boxes[l]) {
                int pre = process3(boxes, last, i - 1, 0, dp);
                int next = process3(boxes, i, r, preK, dp);
                p2 = Math.max(p2, pre + next);
            }
        }
        int ans = Math.max(p1, p2);
        dp[l][r][k] = ans;
        return ans;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 10;
        int maxValue = 5;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generatePositiveRandomArray(maxSize, maxValue);
            int ans1 = removeBoxes(arr);
            int ans2 = removeBoxes(arr);
            int ans3 = removeBoxes3(arr);

            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
            }
        }
    }
}
