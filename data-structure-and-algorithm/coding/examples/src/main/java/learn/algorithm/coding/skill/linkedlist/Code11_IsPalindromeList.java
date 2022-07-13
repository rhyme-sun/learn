package learn.algorithm.coding.skill.linkedlist;

/**
 * 那么如何判断一个链表是否是回文结构。
 * https://leetcode.cn/problems/palindrome-linked-list/
 */
public class Code11_IsPalindromeList {

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode middle = middleNode(head);
        ListNode last = reverse(middle);

        ListNode p1 = head, p2 = last;
        boolean ans = true;
        while (p2 != null) {
            if (p1.val != p2.val) {
                ans = false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        // 还原链表
        reverse(last);
        return ans;
    }

    private static ListNode middleNode(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private static ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    private static void print(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            System.out.println(cur.val);
            cur = cur.next;
        }
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        boolean ans = isPalindrome(head);
        System.out.println(ans);
        print(head);
    }
}
