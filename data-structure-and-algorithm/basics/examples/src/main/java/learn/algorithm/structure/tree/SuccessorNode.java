package learn.algorithm.structure.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树某个节点的后继节点
 */
public class SuccessorNode {

    static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 方法一
     */
    static Node getSuccessorNode1(Node node) {
        if (node == null) {
            return node;
        }
        // 找到二叉树的头节点
        Node cur = node;
        while (cur.parent != null) {
            cur = cur.parent;
        }
        // 中序遍历二叉树
        List<Node> nodeSequence = new ArrayList<>();
        in(cur, nodeSequence);

        for (int i = 0; i < nodeSequence.size(); i++) {
            if (node == nodeSequence.get(i)) {
                return i+1 == nodeSequence.size() ? null : nodeSequence.get(i+1);
            }
        }
        return null;
    }

    static void in(Node head,  List<Node> nodeSequence) {
        if (head == null) {
            return;
        }
        in(head.left, nodeSequence);
        nodeSequence.add(head);
        in(head.right, nodeSequence);
    }

    /**
     * 方法二
     */
    static Node getSuccessorNode(Node node) {
        if (node == null) {
            return node;
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
            return node;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
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
