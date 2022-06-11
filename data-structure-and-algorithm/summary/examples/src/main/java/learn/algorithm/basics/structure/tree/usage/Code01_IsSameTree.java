package learn.algorithm.basics.structure.tree.usage;

/**
 * 判断两棵树是否相同，结构和对应的值都相同
 */
public class Code01_IsSameTree {

    static boolean isSameTree(TreeNode p, TreeNode q) {
        // 一个为空，一个不为空
        if (p == null ^ q == null) {
            return false;
        }
        // 都为空
        if (p == null && q == null) {
            return true;
        }
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
