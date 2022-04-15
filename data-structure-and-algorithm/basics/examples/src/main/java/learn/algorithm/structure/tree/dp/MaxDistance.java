package learn.algorithm.structure.tree.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import learn.algorithm.comparator.BinaryTreeComparator;
import learn.algorithm.structure.tree.Node;

/**
 * 求二叉树最大距离。
 * 对于二叉树任意两个节点，都会有一条路径能够从一个节点到达另外一个节点，沿途经过的节点数量就称为两个节点间的距离。
 * 那么在二叉树中，这样距离的最大值就为二叉树的最大距离。
 */
public class MaxDistance {

    /**
     * 方法 1
     */
    static int maxDistance1(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = getPreList(head);
        HashMap<Node, Node> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    private static ArrayList<Node> getPreList(Node head) {
        ArrayList<Node> arr = new ArrayList<>();
        fillPreList(head, arr);
        return arr;
    }

    private static void fillPreList(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPreList(head.left, arr);
        fillPreList(head.right, arr);
    }

    private static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }

    private static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    private static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        Node lowestAncestor = cur;
        cur = o1;
        int distance1 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }
        cur = o2;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;
    }

    /**
     * 方法 2
     */
    static int maxDistance2(Node head) {
        return process(head).maxDistance;
    }

    /**
     * 某个节点 x 的子树信息，辅助求二叉树的最大距离
     */
    static class Info {
        /**
         * 子树的最大距离
         */
        int maxDistance;
        /**
         * 子树的高度
         */
        int height;

        public Info(int m, int h) {
            maxDistance = m;
            height = h;
        }
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

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            if (maxDistance1(head) != maxDistance2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
