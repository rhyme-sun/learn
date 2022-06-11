package learn.algorithm.basics.structure.tree.usage;

/**
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * <p>
 * https://leetcode.cn/problems/maximum-depth-of-binary-tree/
 */
public class Code03_MaxDepth {

    static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root);
    }

    private static int process(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(process(node.left), process(node.right)) + 1;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
}
