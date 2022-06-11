package learn.algorithm.basics.structure.stack;

import java.util.Objects;
import java.util.Stack;

/**
 * 顺序栈，使用数组存储元素
 */
public class ArrayStack {

    private String[] data;

    /**
     * 栈中元素的数量
     */
    private int size;

    /**
     * 栈容量
     */
    private int capacity;

    public ArrayStack(int initCapacity) {
        this.data = new String[initCapacity];
        this.capacity = initCapacity;
    }

    /**
     * 入栈
     *
     * @param e 元素
     */
    public boolean push(String e) {
        // 栈已满
        if (size == capacity) {
            return false;
        }
        data[size++] = e;
        return true;
    }

    /**
     * 出栈
     */
    public String pop() {
        if (size == 0) {
            return null;
        }
        return data[--size];
    }

    public static void main(String[] args) {
        int testTimes = 500000;
        int maxSize = 100;
        int maxValue = 100;

        for (int i = 0; i < testTimes; i++) {
            ArrayStack myStack = new ArrayStack(maxSize);
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
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("Finish!");
    }
}
