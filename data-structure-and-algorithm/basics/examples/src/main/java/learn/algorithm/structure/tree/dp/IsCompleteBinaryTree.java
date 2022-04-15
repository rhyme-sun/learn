package learn.algorithm.structure.tree.dp;

import java.util.LinkedList;

import learn.algorithm.comparator.BinaryTreeComparator;
import learn.algorithm.structure.tree.Node;

/**
 * 判断二叉树是否为完全二叉树
 * 完全二叉树：要么这一层是满的，要么不满的一层为最后一层，其这层在从左往右变满的状态，这样的二叉树叫做完全二叉树。
 */
public class IsCompleteBinaryTree {

    /**
     * 方法 1
     */
    static boolean isCBT1(Node head) {
        if (head == null) {
            return true;
        }
        LinkedList<Node> queue = new LinkedList<>();
        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        Node l;
        Node r;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;
            if ((leaf && (l != null || r != null))    // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
                    || (l == null && r != null)) {    // 有右节点，无左节点
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            // 第一次遇到孩子不双全，leaf 激活为 true，下次遍历判断就会触发当前节点是否为叶子节点的判断逻辑
            if (l == null || r == null) {
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 某个节点 x 的子树信息，辅助判断整个二叉树是否为完全二叉树
     */
    static class Info {
        /**
         * 子树是否为满二叉树
         */
        boolean isFull;
        /**
         * 子树是否为完全二叉树
         */
        boolean isCBT;
        /**
         * 子树高度
         */
        int height;

        public Info(boolean full, boolean cbt, int h) {
            isFull = full;
            isCBT = cbt;
            height = h;
        }
    }

    /**
     * 方法 2
     */
    static boolean isCBT2(Node head) {
        return process(head).isCBT;
    }

    private static Info process(Node head) {
        if (head == null) {
            return new Info(true, true, 0);
        }
        final Info leftInfo = process(head.left);
        final Info rightInfo = process(head.right);

        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isCBT = false;
        // 左子树满，右子树满，且高度一致，为完全二叉树
        if (isFull) {
            isCBT = true;
        }
        // 左子树完全，右子树满，左子树高度比右子树高 1，为完全二叉树
        if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        }
        // 左子树满，右子树满，左子树高度比右子树高 1，为完全二叉树
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        }
        // 左子树满，右子树完全，左右子树高度一致，为完全二叉树
        if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
            isCBT = true;
        }
        return new Info(isFull, isCBT, height);
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
