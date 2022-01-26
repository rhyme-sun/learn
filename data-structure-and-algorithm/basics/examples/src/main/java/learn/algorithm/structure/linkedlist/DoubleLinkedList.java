package learn.algorithm.structure.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * 双链表
 */
@Slf4j
public class DoubleLinkedList {

    /**
     * 头节点
     */
    private DoubleNode first;
    /**
     * 尾节点
     */
    private DoubleNode last;
    /**
     * 元素个数
     */
    private int size;

    public int size() {
        return size;
    }

    /**
     * 在链表末尾添加元素
     *
     * @param e e
     */
    public void add(int e) {
        DoubleNode newNode = new DoubleNode(e);
        if (Objects.isNull(first)) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    /**
     * 在链表指定位置添加元素
     *
     * @param index index
     * @param e     e
     */
    public void set(int index, int e) {
        final DoubleNode newNode = new DoubleNode(e);
        if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            size++;

            first = newNode;
            return;
        }
        if (index == size) {
            last.next = newNode;
            newNode.prev = last;
            size++;

            last = newNode;
            return;
        }

        final DoubleNode node = node(index);
        newNode.next = node;
        newNode.prev = node.prev;

        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    /**
     * 删除指定位置的元素
     *
     * @param index index
     */
    public void remove(int index) {
        if (index == 0) {
            if (size == 1) {
                first = null;
                last = null;
            } else {
                first.next.prev = null;
                first = first.next;
            }
            size--;
            return;
        }
        if (index == size - 1) {
            if (size == 1) {
                first = null;
                last = null;
            } else {
                last.prev.next = null;
                last = last.prev;
            }
            size--;
            return;
        }

        final DoubleNode node = node(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    /**
     * 删除第一个值等于给定值的节点
     *
     * @param e e
     */
    public void removeElement(int e) {
        DoubleNode node = first;
        for (int i = 0; i < size; i++) {
            if (e == node.value) {
                if (node == first) {
                    if (size == 1) {
                        first = null;
                        last = null;
                    } else {
                        first.next.prev = null;
                        first = first.next;
                    }
                } else if (node == last) {
                    if (size == 1) {
                        first = null;
                        last = null;
                    } else {
                        last.prev.next = null;
                        last = last.prev;
                    }
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
                size--;
                break;
            }
            node = node.next;
        }
    }

    /**
     * 获取指定位置元素
     *
     * @param index index
     * @return 元素值
     */
    public int get(int index) {
        DoubleNode node = node(index);
        return node.value;
    }

    /**
     * 获取指定位置的节点对象
     *
     * @param index index
     * @return 节点对象
     */
    private DoubleNode node(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        DoubleNode node;
        if (index < (size >> 2)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    /**
     * 链表翻转
     */
    public void reverse() {
        DoubleNode prev = null;
        DoubleNode head = first;
        while(head != null) {
            DoubleNode next = head.next;
            head.next = prev;
            head.prev = next;

            prev = head;
            head = next;
        }

        DoubleNode temp = first;
        first = last;
        last = temp;
    }

    @Override
    public String toString() {
        List<Integer> list = new ArrayList<>(size);
        DoubleNode node = first;
        for (int i = 0; i < size; i++) {
            list.add(node.value);
            node = node.next;
        }
        return list.toString();
    }

    /**
     * 双链表节点对象
     */
    private static class DoubleNode {
        public int value;
        public DoubleNode prev;
        public DoubleNode next;

        public DoubleNode(int data) {
            value = data;
        }

        @Override
        public String toString() {
            return "DoubleNode{" +
                    "value=" + value +
                    ", next=" + next +
                    '}';
        }
    }

    private static class TestUtils {

        static DoubleLinkedList generateRandomDoubleList(int len, int value) {
            DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
            int size = (int) (Math.random() * (len + 1));
            if (size == 0) {
                return doubleLinkedList;
            }
            while (size != 0) {
                int e = (int) (Math.random() * (value + 1));
                doubleLinkedList.add(e);
                size--;
            }
            return doubleLinkedList;
        }

        static DoubleLinkedList testReverseDoubleList(DoubleLinkedList linkedList) {
            List<Integer> list = new ArrayList<>(linkedList.size());
            int size = linkedList.size();
            DoubleNode node = linkedList.first;
            for (int i = 0; i < size; i++) {
                list.add(node.value);
                node = node.next;
            }

            DoubleLinkedList reverseDoubleLinkedList = new DoubleLinkedList();
            for (int i = size - 1; i >= 0; i--) {
                reverseDoubleLinkedList.add(linkedList.get(i));
            }
            return reverseDoubleLinkedList;
        }

        static boolean checkDoubleListEqual(DoubleLinkedList linkedList1, DoubleLinkedList linkedList2) {
            final int size = linkedList1.size();
            if (size != linkedList2.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (linkedList1.get(i) != linkedList2.get(i)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        testReverse();
//        testAdd();
//        testSet();
//        testRemove();
//        testRemoveElement();
    }

    private static void testRemoveElement() {
        final DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(0);
        doubleLinkedList.add(1);
        doubleLinkedList.add(2);
        doubleLinkedList.add(3);
        doubleLinkedList.add(4);

        doubleLinkedList.removeElement(2);
        print(doubleLinkedList);

        doubleLinkedList.removeElement(0);
        print(doubleLinkedList);

        doubleLinkedList.removeElement(4);
        print(doubleLinkedList);
    }

    private static void testRemove() {
        final DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(0);
        doubleLinkedList.add(1);
        doubleLinkedList.add(2);
        doubleLinkedList.add(3);
        doubleLinkedList.add(4);

        doubleLinkedList.remove(2);
        print(doubleLinkedList);

        doubleLinkedList.remove(0);
        print(doubleLinkedList);

        doubleLinkedList.remove(2);
        print(doubleLinkedList);
    }

    private static void testSet() {
        final DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(1);
        doubleLinkedList.add(3);

        print(doubleLinkedList);

        doubleLinkedList.set(1, 2);
        print(doubleLinkedList);

        doubleLinkedList.set(0, 0);
        print(doubleLinkedList);

        doubleLinkedList.set(4, 4);
        print(doubleLinkedList);
    }

    private static void testAdd() {
        final DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(0);
        doubleLinkedList.add(1);
        doubleLinkedList.add(2);
        print(doubleLinkedList);
    }

    private static void print(DoubleLinkedList doubleLinkedList) {
        log.info("{}", doubleLinkedList);
        log.info("size:{}", doubleLinkedList.size());
        log.info("first:{}", doubleLinkedList.first);
        log.info("last:{}", doubleLinkedList.last);
    }

    private static void testReverse() {
        int len = 50;
        int value = 100;
        int testTime = 1000000;
        for (int i = 0; i < testTime; i++) {
            final DoubleLinkedList doubleLinkedList = TestUtils.generateRandomDoubleList(len, value);
            final DoubleLinkedList testReverseDoubleList = TestUtils.testReverseDoubleList(doubleLinkedList);

            doubleLinkedList.reverse();
            if (!TestUtils.checkDoubleListEqual(doubleLinkedList, testReverseDoubleList)) {
                log.info("{}", doubleLinkedList);
                log.info("{}", testReverseDoubleList);
                log.info("Oops!");
                break;
            }
        }
        log.info("Nice!");
    }
}
