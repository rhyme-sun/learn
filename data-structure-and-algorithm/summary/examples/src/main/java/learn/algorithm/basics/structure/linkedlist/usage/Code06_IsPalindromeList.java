package learn.algorithm.basics.structure.linkedlist.usage;

import java.util.Stack;

import learn.algorithm.basics.structure.linkedlist.Node;

/**
 * 给定一个单链表的头节点 head，请判断该链表是否为回文结构（即从头遍历链表和从尾遍历链表，会得到相同的遍历结果）。
 */
public class Code06_IsPalindromeList {

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

    static boolean isPalindrome(Node head) {
        if (head == null) {
            return true;
        }
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        Node pre = slow;
        Node cur = slow.next;
        while(cur != null) {
            Node next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        Node reverseHead = pre;
        Node reverseTail = slow;
        Node cur1 = head;
        Node cur2 = reverseHead;

        boolean isPalindrome = true;
        while(cur1 != reverseTail) {
            if (cur1.value != cur2.value) {
                isPalindrome = false;
                break;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }

        pre = null;
        cur = reverseHead;
        while (cur != reverseTail) {
            Node next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return isPalindrome ;
    }

    // for test
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
