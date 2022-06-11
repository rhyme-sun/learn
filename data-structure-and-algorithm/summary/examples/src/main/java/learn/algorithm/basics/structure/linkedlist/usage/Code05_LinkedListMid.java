package learn.algorithm.basics.structure.linkedlist.usage;

import java.util.ArrayList;

import learn.algorithm.basics.structure.linkedlist.Node;
import learn.algorithm.comparator.ArrayComparator;

/**
 * 快慢指针求链表中点
 */
public class Code05_LinkedListMid {

    /**
     * 求链表的上中点
     */
    static Node midOrUpMidNode(Node head) {
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
    static Node midOrDownMidNode(Node head) {
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
    static Node midOrUpMidPreNode(Node head) {
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
     * 求链表下中点前一个
     */
    static Node midOrDownMidPreNode(Node head) {
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
    static Node right1(Node head) {
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
    static Node right2(Node head) {
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
    static Node right3(Node head) {
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
    static Node right4(Node head) {
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
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Node test = new Node(arr[0]);
        Node pre = test;
        for (int i = 1; i < arr.length; i++) {
            pre.next = new Node(arr[i]);
            pre = pre.next;
        }
        ArrayComparator.printArray(arr);
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