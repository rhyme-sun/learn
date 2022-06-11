package learn.algorithm.basics.structure.tree.usage;

/**
 * 给你一个二叉树的根节点 `root` ， 检查它是否轴对称。
 * https://leetcode.cn/problems/symmetric-tree/
 */
public class Code02_IsSymmetricTree {

    static boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    private static boolean isMirror(TreeNode p, TreeNode q) {
        // 一个为空，一个不为空
        if (p == null ^ q == null) {
            return false;
        }
        // 都为空
        if (p == null && q == null) {
            return true;
        }
        return p.val == q.val && isMirror(p.left, q.right) && isMirror(p.right, q.left);
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
}
