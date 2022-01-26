package learn.algorithm.structure.queue;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import lombok.extern.slf4j.Slf4j;

/**
 * 顺序队列，基于数组实现
 */
@Slf4j
public class ArrayQueue {

    private String[] data;

    private int capacity;
    private int size;

    private int getIndex;
    private int putIndex;

    public ArrayQueue(int initCapacity) {
        this.data = new String[initCapacity];
        this.capacity = initCapacity;
    }

    /**
     * 入队
     */
    public boolean push(String e) {
        if (size == capacity) {
            return false;
        }
        data[putIndex] = e;
        size++;
        putIndex = nextIndex(putIndex);
        return true;
    }

    /**
     * 出队
     */
    public String poll() {
        if (size == 0) {
            return null;
        }
        String e = data[getIndex];
        size--;
        getIndex = nextIndex(getIndex);
        return e;
    }

    private int nextIndex(int i) {
        return i < capacity - 1 ? i + 1 : 0;
    }

    public static void main(String[] args) {
        int testTimes = 500000;
        int maxSize = 100;
        int maxValue = 100;

        for (int i = 0; i < testTimes; i++) {
            ArrayQueue myQueue = new ArrayQueue(maxSize);
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
                            log.info("Oops!");
                        }
                    }
                }
            }
        }
        log.info("Finish!");
    }
}
