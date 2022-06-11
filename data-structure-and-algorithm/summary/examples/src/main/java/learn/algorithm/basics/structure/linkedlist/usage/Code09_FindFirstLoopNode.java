package learn.algorithm.basics.structure.linkedlist.usage;

import java.util.HashSet;

import learn.algorithm.basics.structure.linkedlist.Node;

/**
 * 判断一个单链表是否成环，如果不成环返回 null，如果成环，返回第一个入环节点。
 */
public class Code09_FindFirstLoopNode {

    /**
     * 使用快慢指针实现
     * 找到链表第一个入环节点，如果无环，返回 null
     */
    static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        // 如果链表有环，快慢指针会在链表环上某个节点相遇
        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        // 调整 fast 指针（指向头节点，步长设置为 1），再次遍历，相遇的节点即为入环节点
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 使用容器（Set）实现
     */
    static Node getLoopNode2(Node head) {
        if (head == null) {
            return null;
        }
        HashSet<Node> set = new HashSet<>();
        Node cur = head;
        while (cur != null) {
            if (set.contains(cur)) {
                return cur;
            }
            set.add(cur);
            cur = cur.next;
        }
        return null;
    }

    public static void main(String[] args) {
        Node node = new Node(1);
        final Node loopNode = getLoopNode(node);
        final Node loopNode2 = getLoopNode2(node);
        System.out.println(loopNode + " | " + loopNode2);

        node.next = new Node(2);
        node.next.next = new Node(3);
        node.next.next.next = new Node(4);
        node.next.next.next.next = node.next.next;
        System.out.println(getLoopNode(node).value + " | " + getLoopNode2(node).value);
    }
}
