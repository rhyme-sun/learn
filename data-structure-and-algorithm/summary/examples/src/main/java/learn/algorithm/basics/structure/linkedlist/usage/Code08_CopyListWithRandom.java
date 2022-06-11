package learn.algorithm.basics.structure.linkedlist.usage;

import java.util.HashMap;
import java.util.Map;

/**
 * rand 指针可能指向链表中的任意一个节点，也可能指向 null。
 * 给定一个由 Node 节点类型组成的无环单链表的头节点 head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。
 * 要求时间复杂度为 `O(N)`，额外空间复杂度为 `O(1)` 。
 */
public class Code08_CopyListWithRandom {

    static class Node {
        int val;
        Node next;
        Node rand;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.rand = null;
        }
    }

    /**
     * 使用哈希表存储新老节点的映射关系
     */
    static Node copyRandomList1(Node head) {
        // key 老节点
        // value 新节点
        Map<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }
        return map.get(head);
    }

    static Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next;
        // 1 -> 2 -> 3 -> null
        // 1 -> 1' -> 2 -> 2' -> 3 -> 3'
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }
        cur = head;
        Node copy;
        // 1 1' 2 2' 3 3'
        // 依次设置 1' 2' 3' random 指针
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            copy.rand = cur.rand != null ? cur.rand.next : null;
            cur = next;
        }
        Node res = head.next;
        cur = head;
        // 老 新 混在一起，next 方向上，random 正确
        // next 方向上，把新老链表分离
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            cur.next = next;
            copy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }
}
