package learn.algorithm.structure.list;

import java.util.ArrayList;

/**
 * 单链表
 */
public class SinglyLinkedList {

    /**
     * 单链表节点对象
     */
    static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            value = data;
        }
    }

    /**
     * 反转链表
     *
     * @param head 链表头节点
     * @return 新的头节点
     */
    public static Node reverse(Node head) {
        Node pre = null;
        Node next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 从单链表中移除指定的值
     *
     * @param head head
     * @param num  num
     * @return 链表头部
     */
    public static Node remove(Node head, int num) {
        while (head != null) {
            if (head.value != num) {
                break;
            }
            head = head.next;
        }
        // head来到 第一个不需要删的位置
        Node pre = head;
        Node cur = head;
        //
        while (cur != null) {
            if (cur.value == num) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    private static class TestUtils {

        static Node generateRandomLinkedList(int len, int value) {
            int size = (int) (Math.random() * (len + 1));
            if (size == 0) {
                return null;
            }
            size--;
            Node head = new Node((int) (Math.random() * (value + 1)));
            Node pre = head;
            while (size != 0) {
                Node cur = new Node((int) (Math.random() * (value + 1)));
                pre.next = cur;
                pre = cur;
                size--;
            }
            return head;
        }

        static Node testReverseLinkedList(Node head) {
            if (head == null) {
                return null;
            }
            ArrayList<Node> list = new ArrayList<>();
            while (head != null) {
                list.add(head);
                head = head.next;
            }
            list.get(0).next = null;
            int N = list.size();
            for (int i = 1; i < N; i++) {
                list.get(i).next = list.get(i - 1);
            }
            return list.get(N - 1);
        }

        /**
         * 要求无环，有环别用这个函数
         */
        static boolean checkLinkedListEqual(Node head1, Node head2) {
            while (head1 != null && head2 != null) {
                if (head1.value != head2.value) {
                    return false;
                }
                head1 = head1.next;
                head2 = head2.next;
            }
            return head1 == null && head2 == null;
        }
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            Node node = TestUtils.generateRandomLinkedList(len, value);
            Node reverse = reverse(node);
            Node back = TestUtils.testReverseLinkedList(reverse);
            if (!TestUtils.checkLinkedListEqual(node, back)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("Nice!");
    }
}

