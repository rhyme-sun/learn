package learn.algorithm.structure.tree.dp;

import learn.algorithm.comparator.BinaryTreeComparator;
import learn.algorithm.structure.tree.Node;

/**
 * 判断二叉树是否为平衡二叉树
 * 平衡二叉树的定义：：可以为空树，如果不为空树，满足任何一个节点的左子树与右子树都是平衡二叉树，并且高度之差的绝对值不超过 1。
 */
public class IsBalancedBinaryTree {

    /**
     * 方法 1，深度优先遍历
     */
    static boolean isBalanced1(Node root) {
        if (root == null) {
            return true;
        }
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(root, ans);
        return ans[0];
    }

    private static int process1(Node head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int leftHeight = process1(head.left, ans);
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 方法 2，递归套路
     */
    static boolean isBalanced2(Node root) {
        if (root == null) {
            return true;
        }
        return process(root).isBalanced;
    }

    private static Info process(Node head) {
        if (head == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = false;
        if (leftInfo.isBalanced && rightInfo.isBalanced && Math.abs(leftInfo.height - rightInfo.height) <= 1) {
            isBalanced = true;
        }
        return new Info(isBalanced, height);
    }

    /**
     * 某个节点 x 的子树信息，用来存放某棵子树是否平衡和高度信息，辅助判断整个二叉树是否为平衡二叉树
     */
    private static class Info {
        boolean isBalanced;
        int height;

        public Info(boolean i, int h) {
            isBalanced = i;
            height = h;
        }
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finish!");
    }
}
