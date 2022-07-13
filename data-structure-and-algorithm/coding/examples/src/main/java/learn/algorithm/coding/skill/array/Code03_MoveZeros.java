package learn.algorithm.coding.skill.array;

/**
 * 给定一个数组 `nums`，编写一个函数将所有 `0` 移动到数组的末尾，同时保持非零元素的相对顺序。
 * <p>
 * https://leetcode.cn/problems/move-zeroes/
 */
public class Code03_MoveZeros {

    public void moveZeroes(int[] nums) {
        int start = removeElement(nums, 0);
        for (int i = start; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    private int removeElement(int[] nums, int val) {
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;
    }

    public void moveZeroes2(int[] nums) {
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != 0) {
                swap(nums, slow++, fast);
            }
            fast++;
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
