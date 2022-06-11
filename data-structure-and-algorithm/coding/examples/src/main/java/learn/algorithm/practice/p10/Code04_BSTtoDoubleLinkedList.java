package learn.algorithm.practice.p10;

/**
 * 给定一棵搜索二叉树头节点，转化成首尾相接的有序双向链表。
 * <p>
 * https://leetcode.cn/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
 */
public class Code04_BSTtoDoubleLinkedList {

    static Node treeToDoublyList(Node head) {
        if (head == null) {
            return null;
        }
        Info info = process(head);
        info.head.left = info.tail;
        info.tail.right = info.head;
        return info.head;
    }

    private static Info process(Node x) {
        if (x == null) {
            return new Info(null, null);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        if (leftInfo.tail != null) {
            leftInfo.tail.right = x;
        }
        x.left = leftInfo.tail;
        x.right = rightInfo.head;
        if (rightInfo.head != null) {
           rightInfo.head.left = x;
        }
        Node head = leftInfo.head == null ? x : leftInfo.head;
        Node tail = rightInfo.tail == null ? x : rightInfo.tail;
        return new Info(head, tail);
    }

    private static class Info {
        private Node head;
        private Node tail;

        Info (Node h, Node t) {
            head = h;
            tail = t;
        }
    }

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }
}