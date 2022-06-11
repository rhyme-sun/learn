package learn.algorithm.structure.tree.dp;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

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
        List<Integer> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i - 1) >= arr.get(i)) {
                return false;
            }
        }
        return true;
    }

    private static void in(Node head, List<Integer> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head.value);
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

    private static Info process(Node head) {
        if (head == null) {
            return new Info(true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        boolean isBST = head.value > leftInfo.max && head.value < rightInfo.min;
        int min = Math.min(head.value, Math.min(leftInfo.min, rightInfo.min));
        int max = Math.max(head.value, Math.max(leftInfo.max, rightInfo.max));
        return new Info(isBST && leftInfo.isBST && rightInfo.isBST, max, min);
    }

    /**
     * 某个节点 x 的子树信息，辅助判断整个二叉树是否为二叉搜索树
     */
    private static class Info {
        boolean isBST;
        int max;
        int min;

        public Info(boolean i, int ma, int mi) {
            isBST = i;
            max = ma;
            min = mi;
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                final boolean isBST1 = isBST1(head);
                final boolean isBST2 = isBST2(head);
                System.out.println("isBST1: " + isBST1);
                System.out.println("isBST2: " + isBST2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finish!");
    }
}
