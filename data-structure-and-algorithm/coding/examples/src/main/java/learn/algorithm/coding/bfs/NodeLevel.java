package learn.algorithm.coding.bfs;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 题目描述如下：
 * 求二叉树上每个节点的层次（根节点的层次为 0）
 */
public class NodeLevel {

    static Map<Node, Integer> nodeLevel(Node head) {
        if (head == null) {
            return null;
        }
        Map<Node, Integer> nodeLevel = new LinkedHashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        int curLevel = 0;
        while (!queue.isEmpty()) {
            // 处理同一个层次的节点
            int levelSize = queue.size();
            while (levelSize-- > 0) {
                Node cur = queue.poll();
                nodeLevel.put(cur, curLevel);

                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
            // 同一层次节点弹出完毕，队列里剩余节点全部是下一层的节点
            curLevel++;
        }
        return nodeLevel;
    }

    private static class Node {

        private int value;
        private Node left;
        private Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void main(String[] args) {
        /**
         *        1
         *       / \
         *      2   3
         *     / \ / \
         *    4  5 6 7
         *   / \
         *  8  9
         */
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);
        Node n9 = new Node(9);

        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;
        n4.left = n8;
        n4.right = n9;

        Node root = n1;
        Map<Node, Integer> levelMap = nodeLevel(root);
        levelMap.forEach((k, v) -> {
            System.out.println(k.value + "->" + v);
        });
    }
}
