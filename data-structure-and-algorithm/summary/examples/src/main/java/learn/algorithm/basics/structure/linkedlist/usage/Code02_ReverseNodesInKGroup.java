package learn.algorithm.basics.structure.linkedlist.usage;

/**
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。k 是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * <p>
 * leetcode: https://leetcode-cn.com/problems/reverse-nodes-in-k-group
 */
public class Code02_ReverseNodesInKGroup {

    static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k < 2) {
            return head;
        }
        int index = 0;
        // 处理第一组
        ListNode cur = head;
        while (cur != null && ++index < k) {
            cur = cur.next;
        }
        // 第一组不满 k 个
        if (index < k) {
            return head;
        }
        Info first = reverse(head, cur.next);
        Info pre = first;
        cur = pre.nextStart;
        while (cur != null) {
            if (++index % k == 0) {
                Info group = reverse(pre.nextStart, cur.next);
                pre.end.next = group.start;
                pre = group;
                cur = group.nextStart;
            } else {
                cur = cur.next;
            }
        }
        // 处理最后一个不足 k 的组
        if (pre.nextStart != null) {
            pre.end.next = pre.nextStart;
        }
        return first.start;
    }

    /**
     * 翻转 start ~ nextStart
     *
     * @param start     这一组的开始
     * @param nextStart 下一组的开始
     * @return Info
     */
    private static Info reverse(ListNode start, ListNode nextStart) {
        ListNode prev = null;
        ListNode cur = start;
        while (cur != nextStart) {
            ListNode next = cur.next;

            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return new Info(prev, start, nextStart);
    }

    private static class Info {
        // 这一组的开始
        ListNode start;
        // 这一组的结尾
        ListNode end;
        // 下一组起始位置
        ListNode nextStart;

        Info(ListNode s, ListNode e, ListNode n) {
            start = s;
            end = e;
            nextStart = n;
        }
    }

    private static class ListNode {
        public int val;
        public ListNode next;

        ListNode(int v) {
            val = v;
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int k = 6;
        ListNode head = new ListNode(arr[0]);
        ListNode pre = head;
        for (int i = 1; i < arr.length; i++) {
            pre.next = new ListNode(arr[i]);
            pre = pre.next;
        }

        ListNode node = reverseKGroup(head, k);
        System.out.println();
    }
}
