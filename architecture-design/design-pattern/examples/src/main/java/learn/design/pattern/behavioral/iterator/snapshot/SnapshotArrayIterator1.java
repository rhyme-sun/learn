package learn.design.pattern.behavioral.iterator.snapshot;

import java.util.Iterator;
import java.util.Objects;

/**
 * 实现一个支持快照功能的迭代器，所谓“快照”，指我们为容器创建迭代器的时候，相当于给容器拍了一张快照（Snapshot）。
 * 之后即便我们增删容器中的元素，快照中的元素并不会做相应的改动。而迭代器遍历的对象是快照而非容器，这样就避免了在使用迭代器遍历的过程中，
 * 增删容器中的元素，导致的不可预期的结果或者报错。
 *
 * <p>实现方式一：在迭代器类中定义一个成员变量 snapshot 来存储快照。每当创建迭代器的时候，都拷贝一份容器中的元素到快照中，
 * 后续的遍历操作都基于这个迭代器自己持有的快照来进行。
 *
 * <p>这个解决方案虽然简单，但代价也有点高。每次创建迭代器的时候，都要拷贝一份数据到快照中，会增加内存的消耗。如果一个容器同时有多个迭代器
 * 在遍历元素，就会导致数据在内存中重复存储多份。不过，庆幸的是，Java 中的拷贝属于浅拷贝，也就是说，容器中的对象并非真的拷贝了多份，
 * 而只是拷贝了对象的引用而已。
 *
 * @param <E>
 */
public class SnapshotArrayIterator1<E> implements Iterator<E> {

    /**
     * 游标
     */
    private int cursor;

    /**
     * 容器快照
     */
    private ArrayList1<E> snapshotArrayList = null;

    public SnapshotArrayIterator1(ArrayList1<E> arrayList) {
        Objects.requireNonNull(arrayList, "待拷贝的列表不能为null");
        snapshotArrayList = new ArrayList1<>();
        // 此处不能使用迭代器遍历，会导致栈溢出
        for (int i = 0; i < arrayList.size(); i++) {
            snapshotArrayList.add(arrayList.get(i));
        }
        this.cursor = 0;
    }

    @Override
    public boolean hasNext() {
        return cursor < snapshotArrayList.size();
    }

    @Override
    public E next() {
        // 返回当前元素，并且游标后移一位
        return snapshotArrayList.get(cursor++);
    }
}

