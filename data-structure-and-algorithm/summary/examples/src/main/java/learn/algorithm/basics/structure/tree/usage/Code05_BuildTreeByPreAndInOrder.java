package learn.algorithm.basics.structure.tree.usage;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 * https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal
 */
public class Code05_BuildTreeByPreAndInOrder {

    private final static Map<Integer, Integer> inoderIndex = new HashMap<>();

    static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0 ||
                preorder.length != inorder.length) {
            return null;
        }
        int n = preorder.length;
        for (int i = 0; i < n; i++) {
            inoderIndex.put(inorder[i], i);
        }
        return process(preorder, 0, n - 1, inorder, 0, n - 1);
    }

    // 在先序的 [preStart,preEnd] 范围和中序的 [inStart,inEnd] 范围构建树
    private static TreeNode process(int[] pre, int preStart, int preEnd,
                                    int[] in, int inStart, int inEnd) {
        if (inStart > inEnd) {
            return null;
        }
        if (inStart == inEnd) {
            return new TreeNode(pre[preStart]);
        }
        TreeNode head = new TreeNode(pre[preStart]);
        int index = inoderIndex.get(pre[preStart]);
        int leftSize = index - inStart;
        head.left = process(pre, preStart + 1, preStart + leftSize, in, inStart, index - 1);
        head.right = process(pre, preStart + leftSize + 1, preEnd, in, index + 1, inEnd);
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
