package learn.algorithm.coding.skill.linkedlist;

/**
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 *
 * https://leetcode.cn/problems/reverse-nodes-in-k-group
 */
public class Code10_ReverseListKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        // 遍历到第 k 个节点
        ListNode a = head, b = head;
        // 遍历到第 k 个节点
        for (int i = 0; i < k; i++) {
            // 不足 k 个，不需要反转，base case
            if (b == null) return head;
            b = b.next;
        }
        // b 来到第 k + 1 个节点
        ListNode newHead = reverse(a, b);
        // 第 k 个节点指向下一组头结点
        a.next = reverseKGroup(b, k);
        return newHead;
    }

    // 翻转 [a,b) 范围内的节点
    private ListNode reverse(ListNode a, ListNode b) {
        ListNode pre = null;
        ListNode cur = a;
        while (cur != b) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
