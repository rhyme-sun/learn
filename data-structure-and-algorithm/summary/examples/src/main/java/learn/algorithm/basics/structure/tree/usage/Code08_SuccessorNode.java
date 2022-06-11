package learn.algorithm.basics.structure.tree.usage;

import java.util.ArrayList;
import java.util.List;

/**
 * 求二叉树某个节点的后继节点。
 * 后继节点的定义：对于二叉树某个节点，其在中序遍历序列中的后个节点就为该节点的后继节点。
 * 现要求给定二叉树上任意一个节点，找到其后继节点。
 */
public class Code08_SuccessorNode {

    /**
     * 方法一，模拟
     */
    static Node getSuccessorNode1(Node node) {
        if (node == null) {
            return null;
        }
        // 找到二叉树的根节点
        Node root = node;
        while (root.parent != null) {
            root = root.parent;
        }
        // 中序遍历二叉树
        List<Node> nodes = new ArrayList<>();
        in(root, nodes);

        for (int i = 0; i < nodes.size(); i++) {
            if (node == nodes.get(i)) {
                return i + 1 == nodes.size() ? null : nodes.get(i + 1);
            }
        }
        return null;
    }

    static void in(Node head, List<Node> nodeSequence) {
        if (head == null) {
            return;
        }
        in(head.left, nodeSequence);
        nodeSequence.add(head);
        in(head.right, nodeSequence);
    }

    /**
     * 方法二，观察
     */
    static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            return getLeftMost(node.right);
        } else { // 无右子树
            Node parent = node.parent;
            while (parent != null && parent.right == node) { // 当前节点是其父亲节点右孩子
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    /**
     * 查找某个子树最左侧的节点
     */
    static Node getLeftMost(Node node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        System.out.println(test.value + " next1: " + getSuccessorNode1(test).value);
        System.out.println();

        test = head.left.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        System.out.println(test.value + " next1: " + getSuccessorNode1(test).value);
        System.out.println();

        test = head.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        System.out.println(test.value + " next1: " + getSuccessorNode1(test).value);
        System.out.println();

        test = head.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        System.out.println(test.value + " next1: " + getSuccessorNode1(test).value);
        System.out.println();

        test = head.left.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        System.out.println(test.value + " next1: " + getSuccessorNode1(test).value);
        System.out.println();

        test = head;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        System.out.println(test.value + " next1: " + getSuccessorNode1(test).value);
        System.out.println();

        test = head.right.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        System.out.println(test.value + " next1: " + getSuccessorNode1(test).value);
        System.out.println();

        test = head.right.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        System.out.println(test.value + " next1: " + getSuccessorNode1(test).value);
        System.out.println();

        test = head.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        System.out.println(test.value + " next1: " + getSuccessorNode1(test).value);
        System.out.println();

        test = head.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test));
        System.out.println(test.value + " next1: " + getSuccessorNode1(test));
    }
}
