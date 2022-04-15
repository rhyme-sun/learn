package learn.algorithm.structure.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import learn.algorithm.comparator.BinaryTreeComparator;

/**
 * 求二叉树的最大宽度
 */
public class BinaryTreeMaxWidth {

    /**
     * 使用 Map 求二叉树的最大宽度
     */
    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // 存储每个节点对应的层数
        HashMap<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        int curLevel = 1; // 当前你正在统计哪一层的宽度
        int curLevelNodes = 0; // 当前层宽度目前是多少
        int max = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int curNodeLevel = levelMap.get(cur);
            if (cur.left != null) {
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }
            if (curNodeLevel != curLevel) {
                max = Math.max(max, curLevelNodes);
                curLevel++;
                curLevelNodes = 0;
            }
            curLevelNodes++;
        }
        max = Math.max(max, curLevelNodes);
        return max;
    }

    /**
     * 不使用 Map 求二叉树的最大宽度
     */
    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> help = new LinkedList<>();
        help.add(head);
        Node cur = null;    // 指向最后一个入队的节点
        Node curEnd = head; // 指向当前层最后一个节点
        int maxWidth = 1;
        int curLevelNodes = 0; // 当前层的节点数

        while (!help.isEmpty()) {
            Node node = help.poll();
            if (node.left != null) {
                cur = node.left;
                help.add(node.left);
            }
            if (node.right != null) {
                cur = node.right;
                help.add(node.right);
            }
            curLevelNodes++;
            if (node == curEnd) {
                curEnd = cur;
                maxWidth = Math.max(curLevelNodes, maxWidth);
                // maxWidth = Math.max(help.size(), maxWidth);
                curLevelNodes = 0;
            }
        }
        return maxWidth;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                PrintBinaryTree.printTree(head);
                final int maxWidthUseMap = maxWidthUseMap(head);
                final int maxWidthNoMap = maxWidthNoMap(head);
                System.out.println("maxWidthUseMap: " + maxWidthUseMap);
                System.out.println("maxWidthNoMap: " + maxWidthNoMap);
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
