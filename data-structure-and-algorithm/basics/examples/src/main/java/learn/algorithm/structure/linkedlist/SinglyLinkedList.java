package learn.algorithm.structure.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * 单链表
 */
@Slf4j
public class SinglyLinkedList {

    /**
     * 头节点
     */
    private Node first;
    /**
     * 尾节点
     */
    private Node last;

    private int size;

    public SinglyLinkedList() {
    }

    public int size() {
        return size;
    }

    /**
     * 增加元素到链表尾部
     *
     * @param e e
     */
    public void add(int e) {
        final Node node = new Node(e);
        if (Objects.isNull(first)) {
            first = node;
            last = first;
        } else {
            last.next = node;
            last = node;
        }
        size++;
    }

    /**
     * 在链表指定位置出增加元素
     *
     * @param index index
     * @param e     e
     */
    public void set(int index, int e) {
        final Node newNode = new Node(e);
        if (index == 0) {
            newNode.next = first;
            first = newNode;
            size++;
            return;
        }

        final Node prev = node(index - 1);
        newNode.next = prev.next;
        prev.next = newNode;

        if (index == size) {
            last = newNode;
        }
        size++;
    }

    /**
     * 删除链表中指定元素的数据
     *
     * @param index index
     */
    public void remove(int index) {
        if (index == 0) {
            first = first.next;
            size--;
            return;
        }
        final Node prevNode = node(index - 1);
        if (index == size - 1) {
            prevNode.next = null;
            size--;
            last = prevNode;
            return;
        }
        final Node node = node(index);
        prevNode.next = node.next;
        size--;
    }

    /**
     * 删除第一个值等于给定值的节点
     *
     * @param e e
     */
    public void removeElement(int e) {
        Node prev = first;
        Node x = first;
        for (int i = 0; i < size; i++) {
            if (e == x.value) {
                prev.next = x.next;
                if (i == 0) {
                    first = prev.next;
                }
                if (i == size - 1) {
                    last = prev;
                }
                size--;
                break;
            }
            prev = x;
            x = x.next;
        }
    }

    /**
     * 获取指定位置的值
     *
     * @param index index
     * @return value
     */
    public int get(int index) {
        final Node node = node(index);
        return node.value;
    }

    /**
     * 返回指定位置的节点
     *
     * @param index index
     * @return Node
     */
    private Node node(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node x = first;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x;
    }

    /**
     * 链表翻转
     */
    public void reverse() {
        Node prev = null;
        Node head = first;
        last = first;

        while (head != null) {
            Node next = head.next;
            head.next = prev;
            first = head;

            prev = head;
            head = next;
        }
    }

    /**
     * 单链表节点对象
     */
    private static class Node {

        public int value;

        public Node next;

        Node(int data) {
            value = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + next +
                    '}';
        }
    }

    @Override
    public String toString() {
        List<Integer> list = new ArrayList<>(size);
        Node node = first;
        for (int i = 0; i < size; i++) {
            list.add(node.value);
            node = node.next;
        }
        return list.toString();
    }

    private static class TestUtils {

        static SinglyLinkedList generateRandomLinkedList(int len, int value) {
            SinglyLinkedList linkedList = new SinglyLinkedList();
            int size = (int) (Math.random() * (len + 1));
            if (size == 0) {
                return linkedList;
            }
            while (size != 0) {
                int e = (int) (Math.random() * (value + 1));
                linkedList.add(e);
                size--;
            }
            return linkedList;
        }

        static SinglyLinkedList testReverseLinkedList(SinglyLinkedList singlyLinkedList) {
            if (singlyLinkedList.size() == 0) {
                return singlyLinkedList;
            }
            SinglyLinkedList reverseLinkedList = new SinglyLinkedList();
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < singlyLinkedList.size(); i++) {
                int e = singlyLinkedList.get(i);
                list.add(e);
            }
            for (int index = list.size() - 1; index >= 0; index--) {
                reverseLinkedList.add(list.get(index));
            }
            return reverseLinkedList;
        }

        static boolean checkLinkedListEqual(SinglyLinkedList list1, SinglyLinkedList list2) {
            final int size = list1.size();
            if (size != list2.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (list1.get(i) != list2.get(i)) {
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
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
        singlyLinkedList.add(0);
        singlyLinkedList.add(1);
        singlyLinkedList.add(2);
        singlyLinkedList.add(3);
        singlyLinkedList.add(4);
        print(singlyLinkedList);

        singlyLinkedList.removeElement(3);
        print(singlyLinkedList);

        singlyLinkedList.removeElement(0);
        print(singlyLinkedList);

        singlyLinkedList.removeElement(4);
        print(singlyLinkedList);
    }

    private static void testRemove() {
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
        singlyLinkedList.add(0);
        singlyLinkedList.add(1);
        singlyLinkedList.add(2);
        singlyLinkedList.add(3);
        singlyLinkedList.add(4);
        print(singlyLinkedList);

        singlyLinkedList.remove(1);
        print(singlyLinkedList);

        singlyLinkedList.remove(0);
        print(singlyLinkedList);

        singlyLinkedList.remove(singlyLinkedList.size() - 1);
        print(singlyLinkedList);
    }

    private static void testAdd() {
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
        singlyLinkedList.add(1);
        singlyLinkedList.add(2);
        singlyLinkedList.add(3);
        print(singlyLinkedList);
    }

    private static void testSet() {
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
        singlyLinkedList.add(1);
        singlyLinkedList.add(3);

        singlyLinkedList.set(1, 2);
        print(singlyLinkedList);

        singlyLinkedList.set(0, 0);
        print(singlyLinkedList);

        singlyLinkedList.set(4, 4);
        print(singlyLinkedList);
    }

    private static void print(SinglyLinkedList singlyLinkedList) {
        log.info("{}", singlyLinkedList);
        log.info("size: {}", singlyLinkedList.size());
        log.info("first: {}", singlyLinkedList.first);
        log.info("last: {}", singlyLinkedList.last);
    }

    private static void testReverse() {
        int len = 10;
        int value = 100;
        int testTime = 1000000;
        for (int i = 0; i < testTime; i++) {
            final SinglyLinkedList linkedList = TestUtils.generateRandomLinkedList(len, value);
            final SinglyLinkedList reverseLinkedList = TestUtils.testReverseLinkedList(linkedList);
            linkedList.reverse();

            if (!TestUtils.checkLinkedListEqual(linkedList, reverseLinkedList)) {
                log.info("Oops!");
                break;
            }
        }
        log.info("Nice!");
    }
}
