package learn.design.pattern.structural.adapter;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * 迭代器适配器，基于组合适配
 * 迭代器（Iterator）通过迭代器适配器（IteratorAdapter）适配枚举器（Enumeration）
 */
public class IteratorAdapter implements Enumeration {

    Iterator iterator;

    public IteratorAdapter(Iterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    @Override
    public Object nextElement() {
        return iterator.next();
    }
}
