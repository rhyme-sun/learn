package learn.algorithm.basics.structure.linkedlist.usage;

import learn.algorithm.basics.structure.linkedlist.Node;

/**
 * 链表分区，将单向链表按某值划分成左边小、中间相等、右边大的形式
 */
public class Code07_SmallerEqualBigger {

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

    static Node listPartition2(Node head, int value) {
        if (head == null) {
            return null;
        }
        Node sH = null, sT = null, eH = null, eT = null, bH = null, bT = null, cur = head;
        while (cur != null) {
            Node next = cur.next;
            // 清除节点原来 next 指针  import!!
            cur.next = null;
            if (cur.value == value) {
                if (eH == null) {
                    eH = cur;
                    eT = cur;
                } else {
                    eT.next = cur;
                    eT = cur;
                }
            } else if (cur.value < value) {
                if (sH == null) {
                    sH = cur;
                    sT = cur;
                } else {
                    sT.next = cur;
                    sT = cur;
                }
            } else {
                if (bH == null) {
                    bH = cur;
                    bT = cur;
                } else {
                    bT.next = cur;
                    bT = cur;
                }
            }
            cur = next;
        }
        head = sH == null ? (eH == null ? bH : eH) : sH;
        if (head == sH) {
            if (eH == null) {
                sT.next = bH;
            } else {
                sT.next = eH;
                eT.next = bH;
            }
        } else if (head == eH) {
            eT.next = bH;
        }
        return head;
    }

    // for test
    private static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 2, 3, -1};
        Node head1 = new Node(arr[1]);
        Node head2 = new Node(arr[1]);

        Node pre1 = head1;
        Node pre2 = head2;
        for (int i = 1; i < arr.length; i++) {
            pre1.next = new Node(arr[i]);
            pre2.next = new Node(arr[i]);

            pre1 = pre1.next;
            pre2 = pre2.next;
        }
        head1 = listPartition1(head1, 0);
        printLinkedList(head1);
        head2 = listPartition2(head2, 0);
        printLinkedList(head2);
    }
}