package learn.algorithm.basics.structure.tree.dp;

import java.util.ArrayList;
import java.util.List;

import learn.algorithm.basics.structure.tree.Node;
import learn.algorithm.comparator.BinaryTreeComparator;

/**
 * 求二叉树最大距离。
 * 对于二叉树任意两个节点，都会有一条路径能够从一个节点到达另外一个节点，沿途经过的节点数量就称为两个节点间的距离。
 * 那么在二叉树中，这样距离的最大值就为二叉树的最大距离。
 */
public class Code06_MaxDistance {

    static int maxDistance1(Node root) {
        if (root == null) {
            return 0;
        }
        List<Node> nodes = getPreList(root);
        int ans = 1;
        for (Node node : nodes) {
            // 以 node 为最近公共祖先的最大距离为左右子树最大高度之和加一
            int maxDistance = maxHeight(node.left) + maxHeight(node.right) + 1;
            ans = Math.max(ans, maxDistance);
        }
        return ans;
    }

    // 以 head 为头结点的最大高度，head 高度为 1
    private static int maxHeight(Node head) {
        if (head == null) {
            return 0;
        }
        return Math.max(maxHeight(head.left), maxHeight(head.right)) + 1;
    }

    private static List<Node> getPreList(Node head) {
        List<Node> arr = new ArrayList<>();
        pre(head, arr);
        return arr;
    }

    private static void pre(Node head, List<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        pre(head.left, arr);
        pre(head.right, arr);
    }

    /**
     * 方法 2
     */
    static int maxDistance2(Node head) {
        return process(head).maxDistance;
    }

    private static Info process(Node x) {
        if (x == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.height + rightInfo.height + 1;
        int maxDistance = Math.max(Math.max(p1, p2), p3);
        return new Info(maxDistance, height);
    }

    /**
     * 某个节点 x 的子树信息，辅助求二叉树的最大距离
     */
    private static class Info {
        int maxDistance;
        int height;

        public Info(int m, int h) {
            maxDistance = m;
            height = h;
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            int ans1 = maxDistance1(head);
            int ans2 = maxDistance2(head);
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
