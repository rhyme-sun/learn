package learn.algorithm.structure.tree.dp;

import java.util.ArrayList;

import learn.algorithm.comparator.BinaryTreeComparator;
import learn.algorithm.structure.tree.Node;

/**
 * 求最大二叉搜索子树的头节点
 */
public class MaxSubBSTHead {

    /**
     * 方法 1
     */
    static Node maxSubBSTHead1(Node head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) != 0) {
            return head;
        }
        Node leftAns = maxSubBSTHead1(head.left);
        Node rightAns = maxSubBSTHead1(head.right);
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }

    private static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    private static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    static class Info {
        /**
         * 最大二叉搜索子树头部
         */
        Node maxSubBSTHead;
        /**
         * 最大二叉搜索子树节点数量
         */
        int maxBSTSubtreeSize;
        /**
         * 子树节点最大值
         */
        int max;
        /**
         * 子树节点最小值
         */
        int min;

        public Info(Node maxSubBSTHead, int maxBSTSubtreeSize, int max, int min) {
            this.maxSubBSTHead = maxSubBSTHead;
            this.maxBSTSubtreeSize = maxBSTSubtreeSize;
            this.max = max;
            this.min = min;
        }
    }

    /**
     * 方法 2
     */
    static Node maxSubBSTHead2(Node head) {
        return process(head).maxSubBSTHead;
    }

    private static Info process(Node x) {
        if (x == null) {
            return new Info(null, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        int max = Math.max(Math.max(leftInfo.max, rightInfo.max), x.value);
        int min = Math.min(Math.min(leftInfo.min, rightInfo.min), x.value);
        Node maxSubBSTHead = null;

        int p1 = leftInfo.maxBSTSubtreeSize;
        int p2 = leftInfo.maxBSTSubtreeSize;
        int p3 = -1;

        boolean leftIsBST = leftInfo.maxSubBSTHead == x.left;
        boolean rightIsBST = rightInfo.maxSubBSTHead == x.right;

        if (leftIsBST && !rightIsBST) {
            maxSubBSTHead = leftInfo.maxSubBSTHead;
        }
        if (!leftIsBST && rightIsBST) {
            maxSubBSTHead = rightInfo.maxSubBSTHead;
        }
        if (leftIsBST && rightIsBST) {
            maxSubBSTHead = leftInfo.maxBSTSubtreeSize >= rightInfo.maxBSTSubtreeSize ?
                    leftInfo.maxSubBSTHead : rightInfo.maxSubBSTHead;
            if (leftInfo.max < x.value && x.value < rightInfo.min) {
                p3 = leftInfo.maxBSTSubtreeSize + rightInfo.maxBSTSubtreeSize + 1;
                maxSubBSTHead = x;
            }
        }
        return new Info(maxSubBSTHead, Math.max(Math.max(p1, p2), p3), max, min);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) {
                BinaryTreeComparator.printTree(head);
                final Node head1 = maxSubBSTHead1(head);
                final Node head2 = maxSubBSTHead2(head);
                System.out.println("head1: " + head1);
                System.out.println("head2: " + head2);
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
