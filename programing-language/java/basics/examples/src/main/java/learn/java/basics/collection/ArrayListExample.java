package learn.java.basics.collection;

import java.util.ArrayList;

/**
 * ArrayListExample.
 */
public class ArrayListExample {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        // 末尾添加数据，O(1)
        list.add(null);
        // 指定位置添加数据，位置上要求有值，或者是末尾位置
        list.add(0, 0);
        // 覆盖指定位置数据，位置上要求有值
        list.set(1, 1);

        System.out.println(list);
    }
}
