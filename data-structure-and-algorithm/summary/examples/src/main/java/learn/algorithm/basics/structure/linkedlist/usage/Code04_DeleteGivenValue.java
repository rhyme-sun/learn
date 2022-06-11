package learn.algorithm.basics.structure.linkedlist.usage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import learn.algorithm.basics.structure.linkedlist.Node;
import learn.algorithm.comparator.LinkedlistComparator;

/**
 * 链表中删除指定数字
 */
public class Code04_DeleteGivenValue {

    /**
     * 删除单链表值为 value 的全部节点
     *
     * @param head  头节点
     * @param value 删除的值
     * @return 删除后链表头节点
     */
    static Node removeValue(Node head, int value) {
        // head 来到第一个值不为 value 的节点
        while (head != null) {
            if (head.value != value) {
                break;
            }
            head = head.next;
        }
        // 此时的 head 有两种情况：
        // 1 head 为 null
        // 2 head 不为 null，且值不等于 value
        Node pre = null;
        Node cur = head;
        while (cur != null) {
            if (cur.value == value) {
                // 删除当前节点
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int len = 5;
        int maxValue = 100;
        for (int i = 0; i < testTimes; i++) {
            int value = (int) (Math.random() * maxValue);
            Node head = LinkedlistComparator.generateRandomLinkedList(len, maxValue);
            List<Integer> list = LinkedlistComparator.getLinkedListOriginOrder(head);

            List<Integer> list1 = new ArrayList<>(list);
            list1.removeIf(v -> v == value);

            Node newHead = removeValue(head, value);
            List<Integer> list2 = LinkedlistComparator.getLinkedListOriginOrder(newHead);
            if (!Objects.equals(list1, list2)) {
                System.out.println(list);
                System.out.println(value);
                System.out.println("Oops!");
                System.out.println(list1);
                System.out.println(list2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
