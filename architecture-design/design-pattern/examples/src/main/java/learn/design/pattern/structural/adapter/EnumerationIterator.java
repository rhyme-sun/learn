package learn.design.pattern.structural.adapter;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * 枚举适配器，基于组合的适配器
 * 枚举器（Enumeration）可以通过枚举适配器（EnumerationIterator）适配迭代器（Iterator）
 */
public class EnumerationIterator implements Iterator {

    Enumeration enumeration;

    public EnumerationIterator(Enumeration enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    @Override
    public Object next() {
        return enumeration.nextElement();
    }
}
