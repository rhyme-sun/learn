package learn.algorithm.coding.skill.tree;

/**
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * **说明:** 叶子节点是指没有子节点的节点。
 * <p>
 * https://leetcode.cn/problems/maximum-depth-of-binary-tree/submissions/
 */
public class Code01_MaxDepth {

    // 记录最大深度
    int ans = 0;
    // 记录遍历到的节点的深度
    int depth = 0;

    public int maxDepth(TreeNode root) {
        traverse(root);
        return ans;
    }

    void traverse(TreeNode head) {
        if (head == null) {
            return;
        }
        depth++;
        // 最大深度一定是某个叶子节点，到达叶子节点用该节点的深度更新答案
        if (head.left == null && head.right == null) {
            ans = Math.max(ans, depth);
        }
        traverse(head.left);
        traverse(head.right);
        depth--;
    }

    public int maxDepth2(TreeNode root) {
        return process(root);
    }

    // 考虑以为某个节点为头的子树的最大高度
    private int process(TreeNode head) {
        if (head == null) {
            return 0;
        }
        int left = process(head.left);
        int right = process(head.left);
        return Math.max(left, right) + 1;
    }
}
