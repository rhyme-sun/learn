package learn.algorithm.coding.skill.tree;

/**
 * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
 * <p>
 * https://leetcode.cn/problems/invert-binary-tree/
 */
public class Code03_InvertTree {

    TreeNode invertTree(TreeNode root) {
        // 遍历二叉树，交换每个节点的子节点
        traverse(root);
        return root;
    }

    // 二叉树遍历函数
    void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        // 每一个节点需要做的事就是交换它的左右子节点
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        // 遍历框架，去遍历左右子树的节点
        traverse(root.left);
        traverse(root.right);
    }

    TreeNode invertTree2(TreeNode root) {
        return process(root);
    }

    // 翻转以 head 为头节点的子树，返回翻转后的头节点
    TreeNode process(TreeNode head) {
        if (head == null) {
            return null;
        }
        // 翻转左子树
        process(head.left);
        process(head.right);
        TreeNode tmp = head.left;
        head.left = head.right;
        head.right = tmp;
        return head;
    }
}
