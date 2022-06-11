package learn.algorithm.practice.p14;

/**
 * 给你一个未排序的整数数组 `nums` ，请你找出其中没有出现的最小的正整数。
 * 请你实现时间复杂度为 `O(n)` 并且只使用常数级别额外空间的解决方案。
 * <p>
 * https://leetcode.cn/problems/first-missing-positive/
 */
public class Code04_MissingNumber {

    static int firstMissingPositive2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int expect = nums.length + 1;
        int l = -1;
        int r = nums.length;

        int index = 0;
        while (l != r - 1) {
            int cur = nums[index];
            if (cur < 1 || cur > expect - 1) {
                swap(nums, index, --r);
                expect--;
            } else {
                if (cur - 1 == index) {
                    l++;
                    index++;
                } else {
                    if (cur == nums[cur - 1]) {
                        swap(nums, index, --r);
                        expect--;
                    } else {
                        swap(nums, index, cur - 1);
                    }
                }
            }
        }
        return l + 2;
    }

    // 更精简的写法
    // expect 和 index 变量用 r 和 l 表示
    static int firstMissingPositive(int[] arr) {
        // l是盯着的位置
        // 0 ~ L-1有效区
        int L = 0;
        int R = arr.length;
        while (L != R) {
            if (arr[L] == L + 1) {
                L++;
            } else if (arr[L] <= L || arr[L] > R || arr[arr[L] - 1] == arr[L]) { // 垃圾的情况
                swap(arr, L, --R);
            } else {
                swap(arr, L, arr[L] - 1);
            }
        }
        return L + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{7, 8, 9, 11, 12};
        int ans = firstMissingPositive2(nums);
        System.out.println(ans);
    }
}
