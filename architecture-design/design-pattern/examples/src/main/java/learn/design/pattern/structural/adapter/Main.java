package learn.design.pattern.structural.adapter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

/**
 * for test.
 */
public class Main {

    public static void main(String[] args) {
        run2();
    }

    /**
     * 使用迭代器（枚举适配迭代器）遍历 Stack
     */
    private static void run() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        Enumeration<Integer> elements = stack.elements();

        Iterator iterator = new EnumerationIterator(elements);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void run2() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(3);
        arrayList.add(4);
        Iterator<Integer> iterator = arrayList.iterator();

        Enumeration iteratorAdaptor = new IteratorAdapter(iterator);
        while (iteratorAdaptor.hasMoreElements()) {
            System.out.println(iteratorAdaptor.nextElement());
        }
    }
}
