package learn.algorithm.basics.structure.tree.dp;

import java.util.ArrayList;
import java.util.List;

import learn.algorithm.basics.structure.tree.Node;
import learn.algorithm.comparator.BinaryTreeComparator;

/**
 * 某个二叉树可能不是二叉搜索树，但其某个子树可能为二叉搜索树，找到节点数量最多的二叉搜索子树。
 */
public class Code05_MaxSubBST {

    /**
     * 方法 1，暴力解，枚举每个节点，看以其为头结点是否能够成为平衡二叉树，返回最大数量。
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
        List<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    private static void in(Node head, List<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    /**
     * 方法二，递归套路
     */
    static int maxSubBSTSize2(Node root) {
        if (root == null) {
            return 0;
        }
        return process(root).size;
    }

    private static Info process(Node head) {
        if (head == null) {
            return new Info(null, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        Node maxSubBSTHead = leftInfo.size >= rightInfo.size ? leftInfo.head : rightInfo.head;
        int size = Math.max(leftInfo.size, rightInfo.size);
        int max = Math.max(leftInfo.max, rightInfo.max);
        int min = Math.min(leftInfo.min, rightInfo.min);

        if (head.left == leftInfo.head && head.right == rightInfo.head) {
            if (head.value > leftInfo.max && head.value < rightInfo.min) {
                maxSubBSTHead = head;
                size = leftInfo.size + rightInfo.size + 1;
                max = Math.max(head.value, Math.max(leftInfo.max, rightInfo.max));
                min = Math.min(head.value, Math.min(leftInfo.min, rightInfo.min));
            }
        }
        return new Info(maxSubBSTHead, size, max, min);
    }

    private static class Info {
        // 最大搜索子树的头结点
        Node head;
        // 最大搜索子树的节点数量
        int size;
        // 子树的最大值
        int max;
        // 子树的最小值
        int min;

        Info(Node head, int size, int max, int min) {
            this.head = head;
            this.size = size;
            this.max = max;
            this.min = min;
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            int ans1 = maxSubBSTSize1(head);
            int ans2 = maxSubBSTSize2(head);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                BinaryTreeComparator.printTree(head);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
