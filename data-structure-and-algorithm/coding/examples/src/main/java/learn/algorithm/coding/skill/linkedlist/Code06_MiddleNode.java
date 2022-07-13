package learn.algorithm.coding.skill.linkedlist;

/**
 * 给定一个头结点为 `head` 的非空单链表，返回链表的中间结点。
 * 如果有两个中间结点，则返回第二个中间结点（下中点）。
 *
 * https://leetcode.cn/problems/middle-of-the-linked-list/
 */
public class Code06_MiddleNode {

    public ListNode middleNode(ListNode head) {
        // 1->2->3->null
        // 1->2->null
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
