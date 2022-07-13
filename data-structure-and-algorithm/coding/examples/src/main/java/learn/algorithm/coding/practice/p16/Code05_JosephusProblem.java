package learn.algorithm.coding.practice.p16;

/**
 * 约瑟夫环问题
 * 给定一个链表头节点 head，和一个正数 m；从头开始，每次数到 m 就杀死当前节点；然后被杀节点的下一个节点从 1 开始重新数；
 * 周而复始直到只剩一个节点，返回最后的节点。
 */
public class Code05_JosephusProblem {

    static int lastRemaining(int n, int m) {
        return process(n, m) - 1;
    }

    // f(n,m) = (f(n-1,m)+(m-1)%n+1-1)%n+1 = (f(n-1,m)+m-1)%n+1
    private static int process(int n, int m) {
        if (n == 1) {
            return 1;
        }
        return (process(n - 1, m) + m - 1) % n + 1;
    }

    // 上述递归的迭代版本
    // f(n,m) = (f(n-1,m)+(m-1)%n+1-1)%n+1 = (f(n-1,m)+m-1)%n+1
    static int lastRemaining2(int n, int m) {
        // n = 1 f=1
        int ans = 1;
        for (int i = 2; i <= n; i++) {
            // 注意要和 i 取模，而不是 n
            ans = (ans + m - 1) % i + 1;
        }
        return ans - 1;
    }

    // 模拟约瑟夫环的行为，head 为开始出发的节点，m 每次跳的步数，从 1 开始
    static Node josephusKill1(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node last = head;
        while (last.next != head) {
            last = last.next;
        }
        int count = 0;
        // last.next -> head，只剩下一个节点时，head = last
        while (head != last) {
            if (++count == m) {
                last.next = head.next;
                count = 0;
            } else {
                last = last.next;
            }
            head = last.next;
        }
        return head;
    }

    static Node josephusKill2(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node cur = head.next;
        int size = 1; // tmp -> list size
        while (cur != head) {
            size++;
            cur = cur.next;
        }
        int live = process(size, m); // tmp -> service node position
        while (--live != 0) {
            head = head.next;
        }
        head.next = head;
        return head;
    }

    public static void printCircularList(Node head) {
        if (head == null) {
            return;
        }
        System.out.print("Circular List: " + head.value + " ");
        Node cur = head.next;
        while (cur != head) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println("-> " + head.value);
    }

    // 单链表节点
    private static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = head1;
        printCircularList(head1);
        head1 = josephusKill1(head1, 3);
        printCircularList(head1);

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = head2;
        printCircularList(head2);
        head2 = josephusKill2(head2, 3);
        printCircularList(head2);
    }
}