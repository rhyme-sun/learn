package learn.algorithm.basics.structure.linkedlist.usage;

import java.util.ArrayList;
import java.util.List;

import learn.algorithm.basics.structure.linkedlist.DoubleNode;
import learn.algorithm.basics.structure.linkedlist.Node;
import learn.algorithm.comparator.LinkedlistComparator;

/**
 * 链表翻转
 */
public class Code01_ReverseList {

    /**
     * 单链表的翻转
     *
     * @param head 链表头节点
     * @return 翻转后链表头节点
     */
    static Node reverseLinkedList(Node head) {
        Node prev = null;
        Node cur = head;
        while (cur != null) {
            Node next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    /**
     * 双链表的翻转
     *
     * @param head 链表头节点
     * @return 翻转后链表头节点
     */
    static DoubleNode reverseDoubleLinkedList(DoubleNode head) {
        DoubleNode prev = null;
        DoubleNode cur = head;
        while (cur != null) {
            DoubleNode next = cur.next;
            cur.next = prev;
            cur.prev = next;

            prev = cur;
            cur = next;
        }
        return prev;
    }

    /**
     * 使用列表实现单链表翻转
     */
    static Node testReverseLinkedList(Node head) {
        if (head == null) {
            return null;
        }
        List<Node> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        int n = list.size();
        for (int i = 1; i < n; i++) {
            list.get(i).next = list.get(i - 1);
        }
        list.get(0).next = null;
        return list.get(n - 1);
    }

    /**
     * 使用列表实现双单链表翻转
     */
    static DoubleNode testReverseDoubleLinkedList(DoubleNode head) {
        if (head == null) {
            return null;
        }
        List<DoubleNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        int n = list.size();
        for (int i = 1; i < n; i++) {
            list.get(i).next = list.get(i - 1);
            list.get(i - 1).prev = list.get(i);
        }
        list.get(0).next = null;
        list.get(n - 1).prev = null;
        return list.get(n - 1);
    }



    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            Node node1 = LinkedlistComparator.generateRandomLinkedList(len, value);
            List<Integer> list1 = LinkedlistComparator.getLinkedListOriginOrder(node1);
            node1 = reverseLinkedList(node1);
            if (!LinkedlistComparator.checkLinkedListReverse(list1, node1)) {
                System.out.println("Oops1!");
            }

            Node node2 = LinkedlistComparator.generateRandomLinkedList(len, value);
            List<Integer> list2 = LinkedlistComparator.getLinkedListOriginOrder(node2);
            node2 = testReverseLinkedList(node2);
            if (!LinkedlistComparator.checkLinkedListReverse(list2, node2)) {
                System.out.println("Oops2!");
            }

            DoubleNode node3 = LinkedlistComparator.generateRandomDoubleList(len, value);
            List<Integer> list3 = LinkedlistComparator.getDoubleListOriginOrder(node3);
            node3 = reverseDoubleLinkedList(node3);
            if (!LinkedlistComparator.checkDoubleListReverse(list3, node3)) {
                System.out.println("Oops3!");
            }

            DoubleNode node4 = LinkedlistComparator.generateRandomDoubleList(len, value);
            List<Integer> list4 = LinkedlistComparator.getDoubleListOriginOrder(node4);
            node4 = testReverseDoubleLinkedList(node4);
            if (!LinkedlistComparator.checkDoubleListReverse(list4, node4)) {
                System.out.println("Oops4!");
            }
        }
        System.out.println("Finish!");
    }
}
