package learn.algorithm.structure.tree.dp;

import java.util.ArrayList;

import learn.algorithm.comparator.BinaryTreeComparator;
import learn.algorithm.structure.tree.Node;

/**
 * 判断二叉树是否为二叉搜索树
 * 二叉搜索树：可以为空，如果不为空，满足以下性质：
 * <p>
 * 每一棵非空左子树的所有键值小于其根节点的键值；
 * 每一棵非空右子树的所有键值大于其根节点的键值。
 */
public class IsBinarySearchTree {

    /**
     * 方法 1：将二叉树中序遍历，得到的序列值如果是从小到大的，则说明二叉树为二叉搜索树
     */
    static boolean isBST1(Node head) {
        if (head == null) {
            return true;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return false;
            }
        }
        return true;
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
     * 方法 2
     */
    static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    /**
     * 某个节点 x 的子树信息，辅助判断整个二叉树是否为二叉搜索树
     */
    static class Info {
        /**
         * 子树是否为搜索二叉树
         */
        boolean isBST;
        /**
         * 子树中节点的最大值
         */
        int max;
        /**
         * 子树节点中的最小值
         */
        int min;

        public Info(boolean i, int ma, int mi) {
            isBST = i;
            max = ma;
            min = mi;
        }
    }

    private static Info process(Node x) {
        if (x == null) {
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int max = x.value;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
        }
        int min = x.value;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
        }
        boolean isBST = true;
        if (leftInfo != null && !leftInfo.isBST) {
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST) {
            isBST = false;
        }
        if (leftInfo != null && leftInfo.max >= x.value) {
            isBST = false;
        }
        if (rightInfo != null && rightInfo.min <= x.value) {
            isBST = false;
        }
        return new Info(isBST, max, min);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1;
        for (int i = 0; i < testTimes; i++) {
             Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                final boolean isBST1 = isBST1(head);
                final boolean isBST2 = isBST2(head);
                System.out.println("isBST1: " + isBST1);
                System.out.println("isBST2: " + isBST2);
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
