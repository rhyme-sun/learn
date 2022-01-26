package learn.algorithm.structure.stack;

import java.util.Objects;
import java.util.Stack;

import lombok.extern.slf4j.Slf4j;

/**
 * 链式栈，使用链表存储元素
 */
@Slf4j
public class LinkedStack {

    private Node top;

    private int capacity;
    private int size;

    public LinkedStack(int initCapacity) {
        this.capacity = initCapacity;
    }

    /**
     * 入栈
     * 1<-2<-3<-4
     */
    public boolean push(String e) {
        // 栈已满
        if (size == capacity) {
            return false;
        }
        top = new Node(e, top);
        size++;
        return true;
    }

    /**
     * 出栈
     */
    public String pop() {
        if (size == 0) {
            return null;
        }
        String data = top.data;
        top = top.last;
        size--;
        return data;
    }

    static class Node {

        String data;
        Node last;

        public Node(String data, Node last) {
            this.data = data;
            this.last = last;
        }
    }

    public static void main(String[] args) {
        int testTimes = 500000;
        int maxSize = 100;
        int maxValue = 100;

        for (int i = 0; i < testTimes; i++) {
            LinkedStack myStack = new LinkedStack(maxSize);
            Stack<String> stack = new Stack<>();
            for (int j = 0; j < maxSize; j++) {
                String num = (int) (Math.random() * maxValue) + "";
                if (stack.isEmpty()) {
                    myStack.push(num);
                    stack.push(num);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(num);
                        stack.push(num);
                    } else {
                        if (!Objects.equals(stack.pop(), myStack.pop())) {
                            log.info("Oops!");
                        }
                    }
                }
            }
        }
        log.info("Finish!");
    }
}
