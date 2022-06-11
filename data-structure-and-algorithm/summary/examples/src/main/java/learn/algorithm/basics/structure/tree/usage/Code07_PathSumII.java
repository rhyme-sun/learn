package learn.algorithm.basics.structure.tree.usage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有从根节点到叶子节点路径总和等于给定目标和的路径。
 * 叶子节点 是指没有子节点的节点。
 * <p>
 * https://leetcode.cn/problems/path-sum-ii
 */
public class Code07_PathSumII {

    static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> ans = new ArrayList<>();
        process(root, targetSum, new ArrayList<>(), ans);
        return ans;
    }

    private static void process(TreeNode head, int rest, List<Integer> path, List<List<Integer>> ans) {
        path.add(head.val);
        rest -= head.val;
        if (head.left == null && head.right == null) {
            if (rest == 0) {
                ans.add(new ArrayList<>(path));
            }
            return;
        }
        if (head.left != null) {
            process(head.left, rest, path, ans);
            path.remove(path.size() - 1);
        }
        if (head.right != null) {
            process(head.right, rest, path, ans);
            path.remove(path.size() - 1);
        }
    }

    private static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;
    }
}
