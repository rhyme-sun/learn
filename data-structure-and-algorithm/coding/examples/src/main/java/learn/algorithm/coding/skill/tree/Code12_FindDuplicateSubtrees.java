package learn.algorithm.coding.skill.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定一棵二叉树 `root`，返回所有重复的子树。
 * 对于同一类的重复子树，你只需要返回其中任意一棵的根结点即可。
 * 如果两棵树具有相同的结构和相同的结点值，则它们是重复的。
 * <p>
 * https://leetcode.cn/problems/find-duplicate-subtrees
 */
public class Code12_FindDuplicateSubtrees {

    // 存放子树的序列化串和出现次数
    Map<String, Integer> counts = new HashMap<>();
    List<TreeNode> ans = new ArrayList<>();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(ans);
        }
        process(root);
        return new ArrayList<>(ans);
    }

    // 考虑某棵子树，其序列化串是否已经存在，如果存在，收集答案（只收集一次）。
    private String process(TreeNode head) {
        if (head == null) {
            return "#";
        }
        String left = process(head.left);
        String right = process(head.right);
        String serial = left + "," + right + "," + head.val;

        int count = counts.merge(serial, 1, Integer::sum);
        if (count == 2) {
            ans.add(head);
        }
        return serial;
    }
}
