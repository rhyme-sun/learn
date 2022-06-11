package learn.design.pattern.behavioral.iterator.snapshot;

import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        run1();
        run2();
    }


    private static void run1() {
        List<Integer> list = new ArrayList1<>();
        list.add(3);
        list.add(8);
        list.add(2);

        Iterator<Integer> iter1 = list.iterator();//snapshot: 3, 8, 2
        list.remove(new Integer(2));//list：3, 8
        Iterator<Integer> iter2 = list.iterator();//snapshot: 3, 8
        list.remove(new Integer(3));//list：8
        Iterator<Integer> iter3 = list.iterator();//snapshot: 3

        // 输出结果：3 8 2
        while (iter1.hasNext()) {
            System.out.print(iter1.next() + " ");
        }
        System.out.println();

        // 输出结果：3 8
        while (iter2.hasNext()) {
            System.out.print(iter2.next() + " ");
        }
        System.out.println();

        // 输出结果：8
        while (iter3.hasNext()) {
            System.out.print(iter3.next() + " ");
        }
        System.out.println();
    }

    private static void run2() {
        List<Integer> list = new ArrayList2<>();
        list.add(3);
        list.add(8);
        list.add(2);

        Iterator<Integer> iter1 = list.iterator();//snapshot: 3, 8, 2
        list.remove(new Integer(2));//list：3, 8
        Iterator<Integer> iter2 = list.iterator();//snapshot: 3, 8
        list.remove(new Integer(3));//list：8
        Iterator<Integer> iter3 = list.iterator();//snapshot: 3

        // 输出结果：3 8 2
        while (iter1.hasNext()) {
            System.out.print(iter1.next() + " ");
        }
        System.out.println();

        // 输出结果：3 8
        while (iter2.hasNext()) {
            System.out.print(iter2.next() + " ");
        }
        System.out.println();

        // 输出结果：8
        while (iter3.hasNext()) {
            System.out.print(iter3.next() + " ");
        }
        System.out.println();
    }
}
