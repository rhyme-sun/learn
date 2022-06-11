package learn.algorithm.basics.structure.tree.usage;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 求二叉树最大宽度，最大宽度是指最宽的层的节点个数。
 * 每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。
 * <p>
 * https://leetcode.cn/problems/maximum-width-of-binary-tree/
 */
public class Code04_MaxWidth {

    static int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(root, 1, 0));
        int ans = 1;
        int curLevel = 1;
        int left = 0;
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            if (poll.node.left != null) {
                queue.add(new Node(poll.node.left, poll.level + 1, poll.pos * 2 + 1));
            }
            if (poll.node.right != null) {
                queue.add(new Node(poll.node.right, poll.level + 1, poll.pos * 2 + 2));
            }
            if (poll.level != curLevel) {
                curLevel = poll.level;
                left = poll.pos;
            }
            ans = Math.max(ans, poll.pos - left + 1);
        }
        return ans;
    }

    private static class Node {
        TreeNode node;
        int level;
        int pos;

        Node(TreeNode node, int level, int pos) {
            this.node = node;
            this.level = level;
            this.pos = pos;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        root.left = new TreeNode();
        root.left.left = new TreeNode();
        root.left.left.left = new TreeNode();

        root.right = new TreeNode();
        root.right.right = new TreeNode();
        root.right.right.left = new TreeNode();
        int ans = widthOfBinaryTree(root);
        System.out.println(ans);
    }
}
