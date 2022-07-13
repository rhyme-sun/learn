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
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        PriorityQueue<ListNode> queue = new PriorityQueue<>(Comparator.comparing(n -> n.val));
        for (ListNode node : lists) {
            if (node != null) {
                queue.add(node);
            }
        }
        while (!queue.isEmpty()) {
            ListNode poll = queue.poll();
            if (poll.next != null) {
                queue.add(poll.next);
            }
            p.next = poll;
            p = p.next;
        }
        return dummy.next;
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
