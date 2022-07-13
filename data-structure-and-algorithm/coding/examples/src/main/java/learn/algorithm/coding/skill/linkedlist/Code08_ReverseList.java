package learn.algorithm.coding.skill.linkedlist;

/**
 * 给你单链表的头节点 `head` ，请你反转链表，并返回反转后的链表。
 *
 * https://leetcode.cn/problems/reverse-linked-list/
 */
public class Code08_ReverseList {

    // 迭代实现
    public ListNode reverseByLoop(ListNode head) {
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

    // 递归实现，对理解递归行为十分有帮助，由于要使用系统栈，空间复杂度为 O(N)
    public ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode last = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
