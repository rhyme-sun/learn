package learn.algorithm.structure.tree.dp;

import learn.algorithm.comparator.BinaryTreeComparator;
import learn.algorithm.structure.tree.Node;

/**
 * 判断二叉树是否为满二叉树
 * 如果一棵树的高度为 h，那么该树一定有 2^h-1 个节点，满足这样性质的树就叫做满二叉树
 */
public class IsFullBinaryTree {

    /**
     * 方法 1
     */
    static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        int height = h(head);
        int nodes = n(head);
        return (1 << height) - 1 == nodes;
    }

    private static int h(Node head) {
        if (head == null) {
            return 0;
        }
        return Math.max(h(head.left), h(head.right)) + 1;
    }

    private static int n(Node head) {
        if (head == null) {
            return 0;
        }
        return n(head.left) + n(head.right) + 1;
    }

    /**
     * 方法 2
     */
    static boolean isFull2(Node head) {
        if (head == null) {
            return true;
        }
        Info all = process(head);
        return (1 << all.height) - 1 == all.nodes;
    }

    /**
     * 某个节点 x 的子树信息，辅助判断整个二叉树是否为满二叉树
     */
    static class Info {
        /**
         * 子树的高度
         */
        public int height;
        /**
         * 子树的节点数量
         */
        public int nodes;

        public Info(int h, int n) {
            height = h;
            nodes = n;
        }
    }

    private static Info process(Node head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new Info(height, nodes);
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            if (isFull1(head) != isFull2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
