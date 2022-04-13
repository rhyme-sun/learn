package learn.algorithm.structure.linkedlist;

import java.util.Stack;

/**
 * 回文链表判断
 */
public class IsPalindromeList {

    static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 使用栈实现，额外空间负载度为 O(n)
     * 基本思路是遍历链表将链表中的元素放入栈中，然后再次遍历链表，利用栈先近后出的特性，比较链表的元素和栈顶元素，如果中间某次不相等则说明
     * 该链表不是回文结构
     */
    static boolean isPalindrome1(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Stack<Integer> help = new Stack<>();
        // 遍历链表，将元素依次放入栈中
        Node cur = head;
        while (cur != null) {
            help.push(cur.value);
            cur = cur.next;
        }
        // 遍历链表，并弹出栈顶元素，比较值
        while (head != null) {
            if (head.value != help.pop()) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 使用栈实现，额外空间负载度为 O(n/2)
     * 其实将链表下中点及之后的元素放入栈中比较即可判断链表是否是回文结构
     */
    static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 利用快慢指针找到链表的下中点
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // 从下中点开始遍历将链表中的元素放入栈中
        Stack<Integer> help = new Stack<>();
        Node cur = slow;
        while (cur != null) {
            help.push(cur.value);
            cur = cur.next;
        }
        // 遍历栈，将栈顶元素弹出和链表比较
        while (!help.isEmpty()) {
            if (help.pop() != head.value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 不使用栈，判断链表是否为回文结构，额外空间负载度为 O(1)
     * 基本思路为，找到链表的中点，将中点后的链表进行翻转，之后遍历链表前半部分和后半部分（翻转过），依次比较值是否相等；
     * 比较完毕后，将链表翻转会原来的样子。
     */
    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 利用快慢指针找到链表的下中点
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // 翻转下中点之后的链表
        Node prev = slow;
        Node cur = slow.next;
        while (cur != null) {
            Node next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        // 遍历链表
        Node head1 = head;
        Node head2 = prev;

        boolean isPalindrome = true;
        while (head1 != slow) {
            if (head1.value != head2.value) {
                isPalindrome = false;
                break;
            }
            head1 = head1.next;
            head2 = head2.next;
        }
        // 恢复链表
        Node prev2 = null;
        Node cur2 = prev;
        while (cur2 != slow) {
            Node next = cur2.next;
            cur2.next = prev2;
            prev2 = cur2;
            cur2 = next;
        }
        return isPalindrome;
    }

    private static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("链表为空");
        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println();

        System.out.println("链表中只有一个元素");
        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println();

        System.out.println("链表为 1->2");
        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println();

        System.out.println("链表为 1->1");
        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println();

        System.out.println("链表为 1->2-3");
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println();

        System.out.println("链表为 1->2->1");
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println();

        System.out.println("链表为 1->2->3->1");
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println();

        System.out.println("链表为 1->2->2->1");
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println();

        System.out.println("链表为 1->2->3->2->1");
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
    }
}
