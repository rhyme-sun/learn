package learn.algorithm.practice.p03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 题目描述如下：
 * 给定三个参数：二叉树的头节点 head，树上某个节点 target，正数 K。从 target 开始，可以向上走或者向下走，
 * 返回与 target 的距离是 K 的所有节点。
 */
public class Code01_DistanceKNodes {

    static List<Node> distanceKNodes(Node head, Node target, int k) {
        if (head == null || target == null || k < 0) {
            return null;
        }
        if (k == 0) {
            return Collections.singletonList(target);
        }
        // 创建每个节点和其父节点映射表
        Map<Node, Node> parentMap = createParentMap(head);
        // bfs
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        queue.add(target);
        visited.add(target);

        List<Node> nodes = new ArrayList<>();
        int curLevel = 0;
        int levelSize = 1;
        while (!queue.isEmpty()) {
            while (levelSize > 0) {
                Node cur = queue.poll();
                if (curLevel == k) {
                    nodes.add(cur);
                }
                if (cur.left != null && !visited.contains(cur.left)) {
                    queue.add(cur.left);
                    visited.add(cur.left);
                }
                if (cur.right != null && !visited.contains(cur.right)) {
                    queue.add(cur.right);
                    visited.add(cur.right);
                }
                Node parent = parentMap.get(cur);
                if (parent != null && !visited.contains(parent)) {
                    queue.add(parent);
                    visited.add(parent);
                }
                levelSize--;
            }
            levelSize = queue.size();
            curLevel++;
        }
        return nodes;
    }

    /**
     * 创建节点和其父节点映射表
     */
    private static Map<Node, Node> createParentMap(Node head) {
        Map<Node, Node> parents = new HashMap<>();
        parents.put(head, null);
        createParentMap(head, parents);
        return parents;
    }

    private static void createParentMap(Node head, Map<Node, Node> parents) {
        if (head == null) {
            return;
        }
        if (head.left != null) {
            parents.put(head.left, head);
            createParentMap(head.left, parents);
        }
        if (head.right != null) {
            parents.put(head.right, head);
            createParentMap(head.right, parents);
        }
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
        Node target = n2;
        int k = 3;
        List<Node> ans = distanceKNodes(root, target, k);
        for (Node o1 : ans) {
            System.out.println(o1.value);
        }
    }
}
