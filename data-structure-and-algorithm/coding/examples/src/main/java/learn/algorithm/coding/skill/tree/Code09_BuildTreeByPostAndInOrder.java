package learn.algorithm.coding.skill.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定两个整数数组 `inorder` 和 `postorder` ，其中 `inorder` 是二叉树的中序遍历， `postorder` 是同一棵树的后序遍历，请你构造并返回这颗 二叉树 。
 * <p>
 * https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal
 */
public class Code09_BuildTreeByPostAndInOrder {

    private final static Map<Integer, Integer> inorderIndex = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n = inorder.length;
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        return process(inorder, 0, n - 1, postorder, 0, n - 1);
    }

    private TreeNode process(int[] inorder, int inStart, int inEnd,
                             int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd) {
            return null;
        }
        if (inStart == inEnd) {
            return new TreeNode(postorder[postEnd]);
        }
        TreeNode head = new TreeNode(postorder[postEnd]);
        int index = inorderIndex.get(postorder[postEnd]);
        int leftSize = index - inStart;
        head.left = process(inorder, inStart, index - 1, postorder, postStart, postStart + leftSize - 1);
        head.right = process(inorder, index + 1, inEnd, postorder, postStart + leftSize, postEnd - 1);
        return head;
    }
}
