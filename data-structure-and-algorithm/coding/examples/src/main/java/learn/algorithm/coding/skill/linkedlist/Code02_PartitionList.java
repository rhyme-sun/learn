package learn.algorithm.coding.skill.linkedlist;

/**
 * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
 * 你应当保留两个分区中每个节点的初始相对位置。
 *
 * https://leetcode.cn/problems/partition-list
 */
public class Code02_PartitionList {

    public ListNode partition(ListNode head, int x) {
        // 小于 x 的虚拟头节点
        ListNode dummy1 = new ListNode(-1);
        // 大于等于 x 的虚拟头节点
        ListNode dummy2 = new ListNode(-1);
        ListNode p1 = dummy1, p2 = dummy2, p = head;
        while (p != null) {
            if (p.val < x) {
                p1.next = p;
                p1 = p1.next;
            } else {
                p2.next = p;
                p2 = p2.next;
            }
            // 断开原链表中的每个节点的 next 指针
            ListNode temp = p.next;
            p.next = null;
            p = temp;
        }
        p1.next = dummy2.next;
        return dummy1.next;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
