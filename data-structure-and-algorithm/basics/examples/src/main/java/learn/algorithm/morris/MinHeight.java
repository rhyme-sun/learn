package learn.algorithm.morris;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import learn.algorithm.comparator.BinaryTreeComparator;
import learn.algorithm.structure.tree.Node;

/**
 * 求二叉树的最小深度
 */
public class MinHeight {

    /**
     * 递归解决
     */
    static int minHeight1(Node head) {
        if (head == null) {
            return 0;
        }
        return p(head);
    }

    /**
     * 返回 x 为头节点的树的最小深度
     */
    private static int p(Node x) {
        if (x.left == null && x.right == null) {
            return 1;
        }
        // 左右子树起码有一个不为空
        int leftH = Integer.MAX_VALUE;
        if (x.left != null) {
            leftH = p(x.left);
        }
        int rightH = Integer.MAX_VALUE;
        if (x.right != null) {
            rightH = p(x.right);
        }
        return 1 + Math.min(leftH, rightH);
    }

    /**
     * 使用树的层次遍历
     * 统计每个节点的层数，那么所有叶子节点中，层数最小的就是树的最小高度
     */
    static int minHeight2(Node head) {
        if (head == null) {
            return 0;
        }
        Map<Node, Integer> nodeLevel = new HashMap<>();
        // 记录每一个层级最后一个节点
        Node levelEnd = head;
        Deque<Node> queue = new LinkedList<>();
        queue.addLast(head);
        int curLevel = 1;
        while (!queue.isEmpty()) {
            Node node = queue.pollFirst();
            nodeLevel.put(node, curLevel);
            if (node.left != null) {
                queue.addLast(node.left);
            }
            if (node.right != null) {
                queue.addLast(node.right);
            }
            // 上一层的最后一个节点从队列里弹出时，此时队列尾部的节点就是下一层的最后一个节点
            if (node == levelEnd) {
                levelEnd = queue.peekLast();
                curLevel++;
            }
        }
        int minHeight = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : nodeLevel.entrySet()) {
            Node node = entry.getKey();
            if (node.left == null && node.right == null) {
                minHeight = Math.min(minHeight, entry.getValue());
            }
        }
        return minHeight;
    }

    /**
     * 根据 Morris 遍历改写
     */
    static int minHeight3(Node head) {
        if (head == null) {
            return 0;
        }
        Node cur = head;
        int curLevel = 0;
        int minHeight = Integer.MAX_VALUE;
        while (cur != null) {
            Node curL = cur.left;
            if (curL == null) {
                curLevel++;
                cur = cur.right;
            } else {
                int rightBoardSize = 1;
                Node mostRight = curL;
                while (mostRight.right != null && mostRight.right != cur) {
                    rightBoardSize++;
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    // 第一次到达 cur
                    mostRight.right = cur;
                    cur = cur.left;
                    curLevel++;
                } else {
                    // 考察 cur 左子树最右侧节点
                    // 第二次到达 cur（通过 mostRight 回来），且该节点为叶子节点
                    if (mostRight.left == null) {
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    mostRight.right = null;
                    cur = cur.right;
                    // curLevel 为当前 cur 左子树最右侧节点的高度，下个考察的点为 cur 的右节点，所以这里要减去左子树右侧节点数量
                    curLevel -= rightBoardSize;
                }
            }
        }
        // 考察最右侧节点
        cur = head;
        curLevel = 1;
        while (cur.right != null) {
            curLevel++;
            cur = cur.right;
        }
        if (cur.left == null && cur.right == null) {
            minHeight = Math.min(minHeight, curLevel);
        }
        return minHeight;
    }

    public static void main(String[] args) {
        int treeLevel = 7;
        int nodeMaxValue = 5;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(treeLevel, nodeMaxValue);
//            Node head = new Node(1);
//            head.left = new Node(2);
            int ans1 = minHeight1(head);
            int ans2 = minHeight2(head);
            int ans3 = minHeight3(head);
            if (ans1 != ans2 || ans1 != ans3) {
                BinaryTreeComparator.printTree(head);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
            }
        }
        System.out.println("Finish!");
    }
}
