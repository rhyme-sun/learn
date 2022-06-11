package learn.algorithm.basics.structure.tree.usage;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 * https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal
 */
public class Code05_BuildTreeByPreAndInOrder {

    private final static Map<Integer, Integer> inOderIndex = new HashMap<>();

    static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0 ||
                preorder.length != inorder.length) {
            return null;
        }
        int n = preorder.length;
        for (int i = 0; i < n; i++) {
            inOderIndex.put(inorder[i], i);
        }
        return process(preorder, 0, n - 1, inorder, 0, n - 1);
    }

    // 在先序的 [l1,r1] 范围和中序的 [l2,r2] 范围构建树
    private static TreeNode process(int[] pre, int l1, int r1, int[] in, int l2, int r2) {
        if (l1 > r1) {
            return null;
        }
        if (l1 == r1) {
            return new TreeNode(pre[l1]);
        }
        TreeNode head = new TreeNode(pre[l1]);
        int index = inOderIndex.get(pre[l1]);
        int leftSize = index - l2;
        int rightSize = r2 - index;
        head.left = process(pre, l1 + 1, l1 + leftSize, in, l2, index - 1);
        head.right = process(pre, l1 + leftSize + 1, l1 + leftSize + rightSize, in, index + 1, r2);
        return head;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            val = value;
        }
    }
}
