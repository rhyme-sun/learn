package learn.algorithm.structure.linkedlist;

import java.util.ArrayList;

/**
 * 快慢指针求链表中点
 */
public class LinkedListMid {

    static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 求链表的上中点
     */
    public static Node midOrUpMidNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 求链表的下中点
     */
    public static Node midOrDownMidNode(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 求链表上中点前一个
     */
    public static Node midOrUpMidPreNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node fast = head.next.next;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 下中点前一个
     */
    public static Node midOrDownMidPreNode(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node fast = head.next.next;
        Node slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 使用列表求上中点
     */
    public static Node right1(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 1) / 2);
    }

    /**
     * 使用列表求下中点
     */
    public static Node right2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get(arr.size() / 2);
    }

    /**
     * 使用列表求上中点的前一个
     */
    public static Node right3(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 3) / 2);
    }

    /**
     * 使用列表求下中点的前一个
     */
    public static Node right4(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 2) / 2);
    }

    public static void main(String[] args) {
        Node test;
        test = new Node(0);
        test.next = new Node(1);
        test.next.next = new Node(2);
        test.next.next.next = new Node(3);
        test.next.next.next.next = new Node(4);
        test.next.next.next.next.next = new Node(5);
        test.next.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next.next = new Node(7);
        test.next.next.next.next.next.next.next.next = new Node(8);
        test.next.next.next.next.next.next.next.next.next = new Node(9);

        Node ans1;
        Node ans2;

        ans1 = midOrUpMidNode(test);
        ans2 = right1(test);
        System.out.println("上终点：" + (ans1 != null ? ans1.value : "无"));
        System.out.println("上终点：" + (ans2 != null ? ans2.value : "无"));

        ans1 = midOrDownMidNode(test);
        ans2 = right2(test);
        System.out.println("下中点：" + (ans1 != null ? ans1.value : "无"));
        System.out.println("下中点：" + (ans2 != null ? ans2.value : "无"));

        ans1 = midOrUpMidPreNode(test);
        ans2 = right3(test);
        System.out.println("上终点前一个：" + (ans1 != null ? ans1.value : "无"));
        System.out.println("上终点前一个：" + (ans2 != null ? ans2.value : "无"));

        ans1 = midOrDownMidPreNode(test);
        ans2 = right4(test);
        System.out.println("下中点前一个：" + (ans1 != null ? ans1.value : "无"));
        System.out.println("下中点前一个：" + (ans2 != null ? ans2.value : "无"));
    }
}