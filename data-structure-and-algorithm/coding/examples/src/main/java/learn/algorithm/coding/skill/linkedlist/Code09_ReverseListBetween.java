package learn.algorithm.coding.skill.linkedlist;

/**
 * 翻转链表的第 m 个到第 n 个节点之间的部分。
 * <p>
 * https://leetcode.com/problems/reverse-linked-list-ii/
 */
public class Code09_ReverseListBetween {

    // 迭代实现
    public static ListNode reverseBetweenByLoop(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode a = dummy, b = dummy, pre = null;
        while (n-- > 0) {
            if (m-- > 0) {
                pre = a;
                a = a.next;
            }
            b = b.next;
        }
        ListNode next = b.next;
        reverse(a, next);
        pre.next = b;
        a.next = next;
        return dummy.next;
    }

    // pre -> a -> x -> b -> next
    // pre -> a <- x    b -> next
    // 翻转 [a,b) 区间的链表节点，左闭右开
    private static ListNode reverse(ListNode a,  ListNode b) {
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

    // 递归实现
    public ListNode reverseBetween(ListNode head, int m, int n) {
        // m=1 时，相当于翻转链表的前 n 个节点
        if (m == 1) {
            return reverseN(head, n);
        }
        // 递归子问题，缩小边界
        head.next = reverseBetween(head.next, m - 1, n - 1);
        return head;
    }

    ListNode successor = null; // 后驱节点

    // 反转以 head 为起点的 n 个节点，返回新的头结点
    ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            // 记录第 n + 1 个节点
            successor = head.next;
            return head;
        }
        // 以 head.next 为起点，需要反转前 n - 1 个节点
        ListNode last = reverseN(head.next, n - 1);

        head.next.next = head;
        // 让反转之后的 head 节点和后面的节点连起来
        head.next = successor;
        return last;
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
        ListNode ans = reverseBetweenByLoop(head, 1, 5);
        print(ans);
    }
}
