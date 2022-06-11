package learn.design.pattern.behavioral.iterator.snapshot;

import java.util.Iterator;

/**
 * 实现一个支持快照功能的迭代器，所谓“快照”，指我们为容器创建迭代器的时候，相当于给容器拍了一张快照（Snapshot）。
 * 之后即便我们增删容器中的元素，快照中的元素并不会做相应的改动。而迭代器遍历的对象是快照而非容器，这样就避免了在使用迭代器遍历的过程中，
 * 增删容器中的元素，导致的不可预期的结果或者报错。
 *
 * <p>实现方式二：我们可以在容器中，为每个元素保存两个时间戳，一个是添加时间戳 addTimestamp，一个是删除时间戳 delTimestamp。
 * 当元素被加入到集合中的时候，我们将 addTimestamp 设置为当前时间，将 delTimestamp 设置成最大长整型值（Long.MAX_VALUE）。
 * 当元素被删除时，我们将 delTimestamp 更新为当前时间，表示已经被删除。注意，这里只是标记删除，而非真正将它从容器中删除。
 *
 * <p>同时，每个迭代器也保存一个迭代器创建时间戳 snapshotTimestamp，也就是迭代器对应的快照的创建时间戳。当使用迭代器来遍历容器的时候，
 * 只有满足 addTimestamp<snapshotTimestamp<delTimestamp 的元素，才是属于这个迭代器的快照。
 *
 * @param <E>
 */
public class SnapshotArrayIterator2<E> implements Iterator<E> {

    /**
     * 快照时间，单位纳秒，连续取毫秒时间可能一样，导致程序报错，也可以使用自增的序列号
     */
    private long snapshotTimestamp;

    /**
     * 在整个容器中的下标，而非快照中的下标
     */
    private int cursorInAll;

    /**
     * 快照中还有几个元素未被遍历
     */
    private int leftCount;

    /**
     * 遍历容器的引用
     */
    private ArrayList2<E> arrayList;

    public SnapshotArrayIterator2(ArrayList2<E> arrayList) {
        this.cursorInAll = 0;
        this.snapshotTimestamp = System.nanoTime();
        this.leftCount = arrayList.actualSize();
        this.arrayList = arrayList;
    }

    @Override
    public boolean hasNext() {
        return leftCount > 0;
    }

    @Override
    public E next() {
        E currentItem = arrayList.get(cursorInAll);
        justNext();
        return currentItem;
    }

    private void justNext() {
        while (cursorInAll < arrayList.totalSize()) {
            long addTimestamp = arrayList.getAddTimestamp(cursorInAll);
            long delTimestamp = arrayList.getDelTimestamp(cursorInAll);
            if (snapshotTimestamp > addTimestamp && snapshotTimestamp < delTimestamp) {
                leftCount--;
                cursorInAll++;
                break;
            }
            cursorInAll++;
        }
    }
}

