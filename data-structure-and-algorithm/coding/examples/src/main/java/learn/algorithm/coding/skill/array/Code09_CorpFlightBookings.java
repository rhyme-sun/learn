package learn.algorithm.coding.skill.array;

/**
 * 这里有 n 个航班，它们分别从 1 到 n 进行编号。
 * 有一份航班预订表 bookings ，表中第 i 条预订记录 `bookings[i] = [firsti, lasti, seatsi]`
 * 意味着在从 `firsti` 到 `lasti` （包含 `firsti` 和 `lasti` ）的 每个航班上预订了 `seatsi` 个座位。
 *
 * 请你返回一个长度为 n 的数组 answer，里面的元素是每个航班预定的座位总数。
 *
 * https://leetcode.cn/problems/corporate-flight-bookings
 */
public class Code09_CorpFlightBookings {

    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] nums = new int[n];
        Diff diff = new Diff(nums);

        for (int i = 0; i < bookings.length; i++) {
            diff.update(bookings[i][0] - 1, bookings[i][1] - 1, bookings[i][2]);
        }
        return diff.result();
    }

    static class Diff {
        private int[] diff;
        int n;

        Diff(int[] nums) {
            n = nums.length;
            diff = new int[n];
            diff[0] = nums[0];
            for (int i = 1; i < n; i++) {
                diff[i] = nums[i] - nums[i-1];
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
}
