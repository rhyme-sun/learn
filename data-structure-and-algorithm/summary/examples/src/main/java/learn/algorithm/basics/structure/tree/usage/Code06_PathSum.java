package learn.algorithm.basics.structure.tree.usage;

/**
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在**根节点到叶子节点**的路径，
 * 这条路径上所有节点值相加等于目标和 targetSum 。如果存在，返回 true ；否则，返回 false 。
 * <p>
 * https://leetcode.cn/problems/path-sum
 */
public class Code06_PathSum {

    static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return process(root, targetSum);
    }

    private static boolean process(TreeNode head, int rest) {
        rest -= head.val;
        if (head.left == null && head.right == null) { // base case 定义为叶子节点
            return rest == 0;
        }
        boolean ans = false;
        if (head.left != null) {
            ans |= process(head.left, rest);
        }
        if (head.right != null) {
            ans |= process(head.right, rest);
        }
        return ans;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
}
