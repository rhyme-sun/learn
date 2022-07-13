package learn.algorithm.coding.skill.tree;

/**
 * 给定一个不重复的整数数组 `nums` 。 最大二叉树 可以用下面的算法从 `nums` 递归地构建:
 *
 * 创建一个根节点，其值为 `nums` 中的最大值。
 * 递归地在最大值左边的子数组前缀上构建左子树。
 * 递归地在最大值右边的子数组后缀上构建右子树。
 * 返回 `nums` 构建的 最大二叉树 。
 *
 * https://leetcode.cn/problems/maximum-binary-tree
 */
public class Code07_ConstructMaximumBinaryTree {

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return process(nums, 0, nums.length - 1);
    }

    private TreeNode process(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int maxIndex = maxIndex(nums, left, right);
        TreeNode head = new TreeNode(nums[maxIndex]);
        head.left = process(nums, left, maxIndex - 1);
        head.right = process(nums, maxIndex + 1, right);
        return head;
    }

    private int maxIndex(int[] nums, int left, int right) {
        int maxIndex = left;
        for (int i = left + 1; i <= right; i++) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
