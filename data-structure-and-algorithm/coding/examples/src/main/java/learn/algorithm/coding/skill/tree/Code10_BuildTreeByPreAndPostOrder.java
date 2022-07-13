package learn.algorithm.coding.skill.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定两个整数数组，`preorder` 和 `postorder` ，其中 `preorder` 是一个具有无重复值的二叉树的前序遍历，`postorder` 是同一棵树的后序遍历，重构并返回二叉树。
 * 如果存在多个答案，您可以返回其中任何 一个。
 * <p>
 * https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-postorder-traversal
 */
public class Code10_BuildTreeByPreAndPostOrder {

    private Map<Integer, Integer> postorderIndex = new HashMap<>();

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        for (int i = 0; i < postorder.length; i++) {
            postorderIndex.put(postorder[i], i);
        }
        int n = preorder.length;
        return process(preorder, 0, n - 1, postorder, 0, n - 1);
    }

    private TreeNode process(int[] pre, int preStart, int preEnd,
                             int[] post, int postStart, int postEnd) {
        if (preStart > preEnd) {
            return null;
        }
        if (preStart == preEnd) {
            return new TreeNode(pre[preStart]);
        }
        TreeNode head = new TreeNode(pre[preStart]);
        int leftHead = pre[preStart + 1];
        int index = postorderIndex.get(leftHead);
        int leftSize = index - postStart;
        head.left = process(pre, preStart + 1, preStart + leftSize + 1, post, postStart, index);
        head.right = process(pre, preStart + leftSize + 2, preEnd, post, index + 1, postEnd - 1);
        return head;
    }
}
