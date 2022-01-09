package learn.algorithm.structure.queue;

/**
 * 双端队列
 */
public class DoubleEndsQueue {

    static class Node {
        public Node last;
        public Node next;
        public String value;

        public Node(String data) {
            value = data;
        }
    }

    public Node head;
    public Node tail;

    public void addFromHead(String value) {
        Node cur = new Node(value);
        if (head == null) {
            tail = cur;
        } else {
            cur.next = head;
            head.last = cur;
        }
        head = cur;
    }

    public void addFromBottom(String value) {
        Node cur = new Node(value);
        if (head == null) {
            head = cur;
        } else {
            cur.last = tail;
            tail.next = cur;
        }
        tail = cur;
    }

    public String popFromHead() {
        if (head == null) {
            return null;
        }
        Node cur = head;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            cur.next = null;
            head.last = null;
        }
        return cur.value;
    }

    public String popFromBottom() {
        if (head == null) {
            return null;
        }
        Node cur = tail;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.last;
            tail.next = null;
            cur.last = null;
        }
        return cur.value;
    }

    public boolean isEmpty() {
        return head == null;
    }
}