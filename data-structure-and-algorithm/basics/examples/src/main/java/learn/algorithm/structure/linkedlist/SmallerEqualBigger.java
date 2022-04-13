package learn.algorithm.structure.linkedlist;

/**
 * 链表分区，将单向链表按某值划分成左边小、中间相等、右边大的形式
 */
public class SmallerEqualBigger {

    static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 使用容器（数组）实现
     */
    static Node listPartition1(Node head, int pivot) {
        if (head == null) {
            return head;
        }
        Node cur = head;
        int i = 0;
        while (cur != null) {
            i++;
            cur = cur.next;
        }
        Node[] nodeArr = new Node[i];
        cur = head;
        for (i = 0; i < nodeArr.length; i++) {
            nodeArr[i] = cur;
            cur = cur.next;
        }
        arrPartition(nodeArr, pivot);
        for (i = 1; i < nodeArr.length; i++) {
            nodeArr[i - 1].next = nodeArr[i];
        }
        nodeArr[i - 1].next = null;
        return nodeArr[0];
    }

    static void arrPartition(Node[] nodeArr, int pivot) {
        int small = -1;
        int big = nodeArr.length;
        int index = 0;
        while (index != big) {
            if (nodeArr[index].value < pivot) {
                swap(nodeArr, ++small, index++);
            } else if (nodeArr[index].value == pivot) {
                index++;
            } else {
                swap(nodeArr, --big, index);
            }
        }
    }

    private static void swap(Node[] nodeArr, int a, int b) {
        Node tmp = nodeArr[a];
        nodeArr[a] = nodeArr[b];
        nodeArr[b] = tmp;
    }

    /**
     * 不使用容器（数组）实现
     */
    static Node listPartition(Node head, int pivot) {
        if (head == null) {
            return head;
        }
        Node sH = null;
        Node sT = null;
        Node eH = null;
        Node eT = null;
        Node bH = null;
        Node bT = null;
        Node cur = head;
        // 遍历链表，划分小于区、等于区、大于区
        while (cur != null) {
            if (cur.value < pivot) {
                if (sT == null) {
                    sH = cur;
                } else {
                    sT.next = cur;
                }
                sT = cur;
            } else if (cur.value == pivot) {
                if (eT == null) {
                    eH = cur;
                } else {
                    eT.next = cur;
                }
                eT = cur;
            } else {
                if (bT == null) {
                    bH = cur;
                } else {
                    bT.next = cur;
                }
                bT = cur;
            }
            cur = cur.next;
        }
        // 串联小于区、等于区和大于区
        if (sT != null) {
            if (eH != null) {
                sT.next = eH;
                eT.next = bH;
            } else {
                sT.next = bH;
            }
            return sH;
        }
        if (eT != null) {
            eT.next = bT;
            return eH;
        }
        return bH;
    }

    private static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);

        head1 = listPartition1(head1, 4);
        printLinkedList(head1);
        head1 = listPartition(head1, 5);
        printLinkedList(head1);
    }
}