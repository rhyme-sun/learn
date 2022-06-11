package learn.algorithm.basics.structure.queue;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 链式队列
 */
public class LinkedQueue {

    private int capacity;
    private int size;

    private Node head;
    private Node tail;

    public LinkedQueue(int initCapacity) {
        this.capacity = initCapacity;
    }

    /**
     * 入队
     * 1->2->3->4
     */
    public boolean push(String e) {
        if (size == capacity) {
            return false;
        }
        Node node = new Node(e);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    /**
     * 出队
     */
    public String poll() {
        if (head == null) {
            return null;
        }
        String data = head.data;
        head = head.next;
        size--;
        return data;
    }

    static class Node {
        String data;
        Node next;

        public Node(String data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        int testTimes = 500000;
        int maxSize = 100;
        int maxValue = 100;

        for (int i = 0; i < testTimes; i++) {
            LinkedQueue myQueue = new LinkedQueue(maxSize);
            Queue<String> queue = new LinkedList<>();
            for (int j = 0; j < maxSize; j++) {
                String num = (int) (Math.random() * maxValue) + "";
                if (queue.isEmpty()) {
                    myQueue.push(num);
                    queue.offer(num);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.push(num);
                        queue.offer(num);
                    } else {
                        if (!Objects.equals(queue.poll(), myQueue.poll())) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("Finish!");
    }
}
