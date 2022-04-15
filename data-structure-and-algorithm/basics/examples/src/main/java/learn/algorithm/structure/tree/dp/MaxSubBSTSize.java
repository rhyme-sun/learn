package learn.algorithm.structure.tree.dp;

import java.util.ArrayList;

import learn.algorithm.comparator.BinaryTreeComparator;
import learn.algorithm.structure.tree.Node;

/**
 * 最大二叉搜索子树，某个二叉树可能不是二叉搜索树，但其某个子树可能为二叉搜索树，找到节点数量最多的二叉搜索子树。
 */
public class MaxSubBSTSize {

    /**
     * 方法 1
     */
    static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
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

    /**
     * 某个节点 x 的子树信息，用来辅助解决最大二叉子树问题
     */
    static class Info {

        /**
         * 最大二叉搜索子树节点数量
         */
        int maxBSTSubtreeSize;
        /**
         * 子树节点总数
         */
        int allSize;
        /**
         * 子树节点最大值
         */
        int max;
        /**
         * 子树节点最小值
         */
        int min;

        public Info(int m, int a, int ma, int mi) {
            maxBSTSubtreeSize = m;
            allSize = a;
            max = ma;
            min = mi;
        }
    }

    /**
     * 方法 2
     */
    static int maxSubBSTSize2(Node head) {
        return process(head).maxBSTSubtreeSize;
    }

    private static Info process(Node x) {
        if (x == null) {
            return new Info(0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int max = Math.max(Math.max(leftInfo.max, rightInfo.max), x.value);
        int min = Math.min(Math.min(leftInfo.min, rightInfo.min), x.value);
        int allSize = leftInfo.allSize + rightInfo.allSize + 1;

        int p1 = leftInfo.maxBSTSubtreeSize;
        int p2 = rightInfo.maxBSTSubtreeSize;
        int p3 = -1;

        boolean leftIsBST = leftInfo.maxBSTSubtreeSize == leftInfo.allSize;
        boolean rightIsBST = rightInfo.maxBSTSubtreeSize == rightInfo.allSize;
        if (leftIsBST && rightIsBST) {
            if (leftInfo.max < x.value && x.value < rightInfo.min) {
                p3 = leftInfo.allSize + rightInfo.allSize + 1;
            }
        }
        return new Info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            final int maxSubBSTSize1 = maxSubBSTSize1(head);
            final int maxSubBSTSize2 = maxSubBSTSize2(head);

            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                System.out.println("Oops!");
                System.out.println("maxSubBSTSize1: " + maxSubBSTSize1);
                System.out.println("maxSubBSTSize2: " + maxSubBSTSize2);
                BinaryTreeComparator.printTree(head);
            }
        }
        System.out.println("finish!");
    }
}
