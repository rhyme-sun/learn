package learn.algorithm.coding.practice.p19;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 给定两个大小相等的数组 `nums1` 和 `nums2`，`nums1` 相对于 `nums` 的优势可以用满足 `nums1[i] > nums2[i]` 的索引 i 的数目来描述。
 * 返回 `nums1` 的任意排列，使其相对于 `nums2` 的优势最大化。
 *
 * https://leetcode.cn/problems/advantage-shuffle
 */
public class Code06_AdvantageShuffle {

    public int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums1.length;
        Info[] infos = new Info[n];
        for (int i = 0; i < n; i++) {
            infos[i] = new Info(i, nums2[i]);
        }
        Arrays.sort(nums1);
        Arrays.sort(infos, Comparator.comparing((Info info) -> -info.val));


        int left = 0, right = n - 1;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            Info info = infos[i];
            if (nums1[right] > info.val) {
                ans[info.index] = nums1[right--];
            } else {
                ans[info.index] = nums1[left++];
            }
        }
        return ans;
    }

    private static class Info {
        int index;
        int val;

        Info(int i, int v) {
            this.index = i;
            this.val = v;
        }
    }
}
