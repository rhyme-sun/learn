package learn.algorithm.coding.skill.linkedlist;

/**
 * 单链表的倒数第 k 个节点
 */
public class Code04_FindKthFromEnd {

    public ListNode findFromEnd(ListNode head, int k) {
        ListNode p1 = head, p2 = head;
        // p1 先走 k 步
        for (int i = 0; i < k; i++) {
            p1 = p1.next;
        }
        // p1 和 p2 同时走 n - k 步
        while (p1 != null) {
            p2 = p2.next;
            p1 = p1.next;
        }
        // p2 现在指向第 n - k + 1 个节点
        return p2;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
