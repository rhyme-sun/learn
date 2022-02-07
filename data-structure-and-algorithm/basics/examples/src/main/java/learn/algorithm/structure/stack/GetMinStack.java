package learn.algorithm.structure.stack;

import java.util.Stack;

/**
 * 获取栈中最小的元素，要求时间复杂度为 O(1)
 */
public class GetMinStack {

    static class MinStack1 {
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MinStack1() {
            this.stackData = new Stack<>();
            this.stackMin = new Stack<>();
        }

        public void push(int newNum) {
            if (this.stackMin.isEmpty()) {
                this.stackMin.push(newNum);
            } else if (newNum <= this.getMin()) {
                this.stackMin.push(newNum);
            }
            this.stackData.push(newNum);
        }

        public int pop() {
            if (this.stackData.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            int value = this.stackData.pop();
            if (value == this.getMin()) {
                this.stackMin.pop();
            }
            return value;
        }

        public int getMin() {
            if (this.stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return this.stackMin.peek();
        }
    }

    static class MinStack2 {
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MinStack2() {
            this.stackData = new Stack<>();
            this.stackMin = new Stack<>();
        }

        public void push(int newNum) {
            if (this.stackMin.isEmpty()) {
                this.stackMin.push(newNum);
            } else if (newNum < this.getMin()) {
                this.stackMin.push(newNum);
            } else {
                int newMin = this.stackMin.peek();
                this.stackMin.push(newMin);
            }
            this.stackData.push(newNum);
        }

        public int pop() {
            if (this.stackData.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            this.stackMin.pop();
            return this.stackData.pop();
        }

        public int getMin() {
            if (this.stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return this.stackMin.peek();
        }
    }

    public static void main(String[] args) {
        MinStack1 stack1 = new MinStack1();
        stack1.push(3);
        log.info(stack1.getMin());
        stack1.push(4);
        log.info(stack1.getMin());
        stack1.push(1);
        stack1.push(1);
        log.info(stack1.getMin());
        log.info(stack1.pop());
        log.info(stack1.getMin());

        log.info("=============");

        MinStack2 stack2 = new MinStack2();
        stack2.push(3);
        log.info(stack2.getMin());
        stack2.push(4);
        log.info(stack2.getMin());
        stack2.push(1);
        stack2.push(1);
        log.info(stack2.getMin());
        log.info(stack2.pop());
        log.info(stack2.getMin());
    }
}
