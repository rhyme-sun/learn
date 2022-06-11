package learn.algorithm.basics.structure.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 增强堆实现示例
 * 支持存储对象（范型），自定义比较器（决定是大根堆或小根堆），resign 堆（在堆排好顺序后，调用对象内部方法手动修改属性值，重新调整堆中元素顺序）
 */
public class HeapGreater<T> {

    private final ArrayList<T> heap;
    /**
     * 反向索引，记录对象和下标的映射关系，用来支持 resign 堆
     */
    private final HashMap<T, Integer> indexMap;
    private final Comparator<? super T> comp;
    private int heapSize;

    public HeapGreater(Comparator<T> c) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comp = c;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public T pop() {
        T ans = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }

    public void remove(T obj) {
        T replace = heap.get(heapSize - 1);
        int index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        if (obj != replace) {
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
    }

    /**
     * 对象属性改变，重新调整堆结构，heapInsert 和 heapify 得到有效执行的只有一个
     */
    public void resign(T obj) {
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    /**
     * 返回堆内所有元素
     */
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

    /**
     * 向上调整堆（上浮）
     */
    private void heapInsert(int index) {
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 向下调整堆，下沉
     */
    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            // 获取子节点最小值对应下标
            int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
            best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(best, index);
            index = best;
            left = index * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.put(o2, i);
        indexMap.put(o1, j);
    }

    // for test
    private static class Student {
        public int classNo;
        public int age;
        public int id;

        public Student(int c, int a, int i) {
            classNo = c;
            age = a;
            id = i;
        }
    }

    // for test
    private static class StudentComparator implements Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return o1.age - o2.age;
        }
    }

    // for test
    public static void main(String[] args) {
        testHeapSort();
        testResign();
    }

    private static void testHeapSort() {
        Student s1 = new Student(2, 50, 11111);
        Student s2 = new Student(1, 60, 22222);
        Student s3 = new Student(6, 10, 33333);
        Student s4 = new Student(3, 20, 44444);
        Student s5 = new Student(7, 72, 55555);
        Student s6 = new Student(1, 14, 66666);

        PriorityQueue<Student> heap = new PriorityQueue<>(new StudentComparator());
        heap.add(s1);
        heap.add(s2);
        heap.add(s3);
        heap.add(s4);
        heap.add(s5);
        heap.add(s6);
        while (!heap.isEmpty()) {
            Student cur = heap.poll();
            System.out.println(cur.classNo + "," + cur.age + "," + cur.id);
        }

        System.out.println("===============");

        HeapGreater<Student> myHeap = new HeapGreater<>(new StudentComparator());
        myHeap.push(s1);
        myHeap.push(s2);
        myHeap.push(s3);
        myHeap.push(s4);
        myHeap.push(s5);
        myHeap.push(s6);
        while (!myHeap.isEmpty()) {
            Student cur = myHeap.pop();
            System.out.println(cur.classNo + "," + cur.age + "," + cur.id);
        }
    }

    private static void testResign() {
        System.out.println("Test resign");
        // 测试 resign 方法
        int maxValue = 100000;
        int pushTime = 1000000;
        int resignTime = 100;
        HeapGreater<Student> test = new HeapGreater<>(new StudentComparator());
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < pushTime; i++) {
            Student cur = new Student(1, (int) (Math.random() * maxValue), 1000);
            list.add(cur);
            test.push(cur);
        }
        for (int i = 0; i < resignTime; i++) {
            int index = (int) (Math.random() * pushTime);
            list.get(index).age = (int) (Math.random() * maxValue);
            test.resign(list.get(index));
        }
        int preAge = Integer.MIN_VALUE;
        // resign 后，测试堆内是否有序
        while (!test.isEmpty()) {
            Student cur = test.pop();
            if (cur.age < preAge) {
                System.out.println("Oops!");
            }
            preAge = cur.age;
        }
        System.out.println("Finish!");
    }
}
