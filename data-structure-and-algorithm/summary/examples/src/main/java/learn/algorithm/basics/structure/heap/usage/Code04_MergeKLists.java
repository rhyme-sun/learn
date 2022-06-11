package learn.algorithm.basics.structure.heap.usage;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * <p>
 * https://leetcode.cn/problems/merge-k-sorted-lists/
 */
public class Code04_MergeKLists {

    static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparing(k -> k.val));
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.offer(list);
            }
        }
        if (minHeap.isEmpty()) {
            return null;
        }
        ListNode head = minHeap.poll();
        if (head.next != null) {
            minHeap.offer(head.next);
            head.next = null;
        }
        ListNode pre = head;
        while(!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            if (node.next != null) {
                minHeap.offer(node.next);
            }
            pre.next = node;
            node.next = null;
            pre = node;
        }
        return head;
    }

    // 单链表节点
    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
