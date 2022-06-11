package learn.design.pattern.behavioral.iterator.snapshot;

import java.util.AbstractList;
import java.util.Iterator;

public class ArrayList2<E> extends AbstractList<E> {

    private static final Object[] EMPTY_ELEMENTDATA = {};

    private static final long[] EMPTY_ADD_TIMESTAMPS = {};

    private static final long[] EMPTY_DEL_TIMESTAMPS = {};

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 不包含标记删除元素
     */
    private int actualSize;

    /**
     * 包含标记删除的元素
     */
    private int totalSize;

    /**
     * 存储数组
     */
    private Object[] elementData;

    /**
     * 新增元素时间戳数据，单位纳秒，连续取毫秒时间可能一样，导致程序报错，也可以使用自增的序列号
     */
    private long[] addTimestamps;

    /**
     * 删除元素时间戳数据，单位纳秒，连续取毫秒时间可能一样，导致程序报错，也可以使用自增的序列号
     */
    private long[] delTimestamps;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayList2() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.addTimestamps = new long[DEFAULT_CAPACITY];
        this.delTimestamps = new long[DEFAULT_CAPACITY];
        this.totalSize = 0;
        this.actualSize = 0;
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public ArrayList2(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
            this.addTimestamps = new long[DEFAULT_CAPACITY];
            this.delTimestamps = new long[DEFAULT_CAPACITY];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
            this.addTimestamps = EMPTY_ADD_TIMESTAMPS;
            this.delTimestamps = EMPTY_DEL_TIMESTAMPS;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    @Override
    public E get(int i) {
        if (i >= totalSize) {
            throw new IndexOutOfBoundsException();
        }
        return (E) elementData[i];
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    @Override
    public int size() {
        return actualSize;
    }

    @Override
    public Iterator<E> iterator() {
        return new SnapshotArrayIterator2<>(this);
    }

    @Override
    public boolean add(E e) {
        elementData[totalSize] = e;
        addTimestamps[totalSize] = System.nanoTime();
        delTimestamps[totalSize] = Long.MAX_VALUE;
        totalSize++;
        actualSize++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < totalSize; ++i) {
            if (elementData[i].equals(o)) {
                delTimestamps[i] = System.nanoTime();
                actualSize--;
            }
        }
        return true;
    }

    public int actualSize() {
        return this.actualSize;
    }

    public int totalSize() {
        return this.totalSize;
    }

    public long getAddTimestamp(int i) {
        if (i >= totalSize) {
            throw new IndexOutOfBoundsException();
        }
        return addTimestamps[i];
    }

    public long getDelTimestamp(int i) {
        if (i >= totalSize) {
            throw new IndexOutOfBoundsException();
        }
        return delTimestamps[i];
    }
}
