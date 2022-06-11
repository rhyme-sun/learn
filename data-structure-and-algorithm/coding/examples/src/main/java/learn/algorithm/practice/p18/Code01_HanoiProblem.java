package learn.algorithm.practice.p18;

/**
 * 给定一个数组 arr，长度为 N，arr 中的值只有 1，2，3 三种。
 * <p>
 * - `arr[i] == 1`，代表汉诺塔问题中，从上往下第 i 个圆盘目前在左；
 * - `arr[i] == 2`，代表汉诺塔问题中，从上往下第 i 个圆盘目前在中；
 * - `arr[i] == 3`，代表汉诺塔问题中，从上往下第 i 个圆盘目前在右；
 * <p>
 * 那么 arr 整体就代表汉诺塔游戏过程中的一个状况，如果这个状况不是汉诺塔最优解运动过程中的状况，返回  -1，
 * 如果这个状况是汉诺塔最优解运动过程中的状态，返回它是第几个状态。
 */
public class Code01_HanoiProblem {

    // 递归版本
    static int step(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        return process(arr, arr.length, 1, 3, 2);
    }

    // n 个盘子，从 from 经过 other 移动到 to 达到 arr 中的位置需要多少步
    // arr[i] 表示 i+1 号盘子此时在那根柱子上（左 1，中 2，右 3）
    private static int process(int[] arr, int n, int from, int to, int other) {
        if (n == 0) {
            return 0;
        }
        // 最后一个盘子不可能移动到 other
        if (arr[n - 1] == other) {
            return -1;
        }
        if (arr[n - 1] == from) {
            return process(arr, n - 1, from, other, to);
        } else {
            int p1 = (1 << (n - 1)) - 1;
            int p2 = 1;
            int p3 = process(arr, n - 1, other, to, from);
            if (p3 == -1) {
                return -1;
            } else {
                return p1 + p2 + p3;
            }
        }
    }

    // 迭代版本
    static int step2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int from = 1;
        int to = 3;
        int other = 2;

        int n = arr.length;
        int ans = 0;
        while (n > 0) {
            if (arr[n - 1] == other) {
                return -1;
            }
            //  process(arr, n - 1, from, other, to)
            if (arr[n - 1] == from) {
                int tmp = to;
                to = other;
                other = tmp;
            } else {
                // process(arr, n - 1, other, to, from)
                ans += 1 << (n - 1) - 1 + 1;
                int tmp = from;
                from = other;
                other = tmp;
            }
            n--;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {3, 3, 2, 1};
        System.out.println(step(arr));
        System.out.println(step2(arr));
        System.out.println(Integer.valueOf("001"));
    }
}
