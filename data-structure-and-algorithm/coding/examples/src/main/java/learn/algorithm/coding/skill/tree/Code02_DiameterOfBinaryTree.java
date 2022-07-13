package learn.algorithm.coding.skill.tree;

/**
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
 *
 * https://leetcode.cn/problems/diameter-of-binary-tree/
 */
public class Code02_DiameterOfBinaryTree {

    // 直径
    private int ans;

    public int diameterOfBinaryTree(TreeNode root) {
        process(root);
        return ans;
    }

    // 以 head 为头结点子树的最大高度
    private int process(TreeNode head) {
        if (head == null) {
            return 0;
        }
        int left = process(head.left);
        int right = process(head.right);
        ans = Math.max(ans, left + right);
        return Math.max(left, right) + 1;
    }
}
