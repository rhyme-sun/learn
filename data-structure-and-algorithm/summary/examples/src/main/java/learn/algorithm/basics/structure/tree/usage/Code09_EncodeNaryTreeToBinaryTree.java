package learn.algorithm.basics.structure.tree.usage;

import java.util.ArrayList;
import java.util.List;

/**
 * 对应 leetcode 题目地址：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
 */
public class Code09_EncodeNaryTreeToBinaryTree {

    // Encodes an n-ary tree to a binary tree.
    // 将多叉树的子节点放到二叉树左子树的右边界
    public TreeNode encode(Node root) {
        if (root == null) {
            return null;
        }
        TreeNode head = new TreeNode(root.val);
        head.left = en(root.children);
        return head;
    }

    private TreeNode en(List<Node> children) {
        TreeNode head = null;
        TreeNode cur = null;
        for (Node child : children) {
            TreeNode tNode = new TreeNode(child.val);
            if (head == null) {
                head = tNode;
            } else {
                cur.right = tNode;
            }
            cur = tNode;
            cur.left = en(child.children);
        }
        return head;
    }

    // Decodes your binary tree to an n-ary tree.
    public Node decode(TreeNode root) {
        if (root == null) {
            return null;
        }
        return new Node(root.val, de(root.left));
    }

    // 收集左子树的右边界成为孩子节点
    public List<Node> de(TreeNode head) {
        List<Node> children = new ArrayList<>();
        while (head != null) {
            Node cur = new Node(head.val, de(head.left));
            children.add(cur);
            head = head.right;
        }
        return children;
    }

    private static class Node {
        public int val;
        public List<Node> children;

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
