package learn.algorithm.coding.skill.array;

/**
 * 给你一个 **升序排列** 的数组 `nums` ，请你原地删除重复出现的元素，使每个元素 **只出现一次 ，
 * 返回删除后数组的新长度。元素的 **相对顺序应该保持 **一致** 。
 *
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-array/submissions/
 */
public class Code01_RemoveDuplicates {

    public int removeDuplicates(int[] nums) {
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[slow] != nums[fast]) {
                nums[++slow] = nums[fast];
            }
            fast++;
        }
        return slow + 1;
    }
}
