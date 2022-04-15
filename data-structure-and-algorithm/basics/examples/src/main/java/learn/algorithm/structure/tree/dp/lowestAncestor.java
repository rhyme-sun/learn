package learn.algorithm.structure.tree.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import learn.algorithm.comparator.BinaryTreeComparator;
import learn.algorithm.structure.tree.Node;

/**
 * 定一棵二叉树的头节点 head，和另外两个节点 a 和 b，返回 a 和 b 的最低公共祖先。
 * <p>
 * 最低公共祖先可以这么理解，一个节点 a 和一个节点 b，同时向上找，最初汇聚的节点就是这两个节点的公共祖先
 */
public class lowestAncestor {

    /**
     * 方法 1
     */
    static Node lowestAncestor1(Node head, Node o1, Node o2) {
        if (head == null) {
            return null;
        }
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
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
        return cur;
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

    /**
     * 方法 2
     */
    static Node lowestAncestor2(Node head, Node a, Node b) {
        return process(head, a, b).ans;
    }

    /**
     * 某个节点 x 的子树信息，辅助找到节点 a、b 的最低公共祖先
     */
    static class Info {
        /**
         * 子树是否包含节点 a
         */
        boolean findA;
        /**
         * 子树是否包含节点 b
         */
        boolean findB;
        /**
         * 子树是否已经有了我们想要的答案，即找到了最低公共祖先
         */
        Node ans;

        public Info(boolean fA, boolean fB, Node an) {
            findA = fA;
            findB = fB;
            ans = an;
        }
    }

    static Info process(Node x, Node a, Node b) {
        if (x == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(x.left, a, b);
        Info rightInfo = process(x.right, a, b);
        boolean findA = (x == a) || leftInfo.findA || rightInfo.findA;
        boolean findB = (x == b) || leftInfo.findB || rightInfo.findB;
        Node ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else {
            if (findA && findB) {
                ans = x;
            }
        }
        return new Info(findA, findB, ans);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            Node o1 = BinaryTreeComparator.pickRandomOne(head);
            Node o2 = BinaryTreeComparator.pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
