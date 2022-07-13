package learn.algorithm.coding.skill.array;

/**
 * 车上最初有 capacity 个空座位。车只能向一个方向行驶（也就是说，不允许掉头或改变方向）。
 * 给定整数 capacity 和一个数组 `trips` ,  `trip[i] = [numPassengersi, fromi, toi]` 表示第 i 次旅行有 `numPassengersi` 乘客，
 * 接他们和放他们的位置分别是 `fromi` 和 `toi` 。这些位置是从汽车的初始位置向东的公里数。
 * <p>
 * 当且仅当你可以在所有给定的行程中接送所有乘客时，返回 true，否则请返回 false。
 * <p>
 * https://leetcode.cn/problems/car-pooling
 */
public class Code10_CarPooling {

    public static boolean carPooling(int[][] trips, int capacity) {
        int[] nums = new int[1000];
        Diff diff = new Diff(nums);
        for (int[] trip : trips) {
            diff.update(trip[1], trip[2] - 1, trip[0]);
        }
        for (int cap : diff.result()) {
            if (cap > capacity) {
                return false;
            }
        }
        return true;
    }

    static class Diff {
        private int[] diff;
        int n;

        Diff(int[] nums) {
            n = nums.length;
            diff = new int[n];
            diff[0] = nums[0];
            for (int i = 1; i < n; i++) {
                diff[i] = nums[i] - nums[i - 1];
            }
        }

        void update(int i, int j, int k) {
            diff[i] += k;
            if (j + 1 < n) {
                diff[j + 1] -= k;
            }
        }

        int[] result() {
            int[] result = new int[n];
            result[0] = diff[0];
            for (int i = 1; i < n; i++) {
                result[i] = diff[i] + result[i - 1];
            }
            return result;
        }
    }

    public static void main(String[] args) {
        int[][] trips = {{0, 0, 5}, {3, 3, 7}};
        int n = 5;
        System.out.println(carPooling(trips, n));
    }
}
