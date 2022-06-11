package learn.design.pattern.behavioral.iterator;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayIterator<E> implements Iterator<E> {

    private int cursor;

    private ArrayList<E> arrayList;

    public ArrayIterator(ArrayList<E> arrayList) {
        this.cursor = 0;
        this.arrayList = arrayList;
    }

    @Override
    public boolean hasNext() {
        // 注意这里，cursor在指向最后一个元素的时候，hasNext()仍旧返回true。
        return cursor != arrayList.size();
    }

    @Override
    public void next() {
        cursor++;
    }

    @Override
    public E currentItem() {
        if (cursor >= arrayList.size()) {
            throw new NoSuchElementException();
        }
        return arrayList.get(cursor);
    }
}