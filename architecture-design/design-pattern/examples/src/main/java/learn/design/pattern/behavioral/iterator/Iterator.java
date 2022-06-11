package learn.design.pattern.behavioral.iterator;

/**
 * 迭代器接口
 */
public interface Iterator<E> {

    boolean hasNext();

    void next();

    E currentItem();
}
