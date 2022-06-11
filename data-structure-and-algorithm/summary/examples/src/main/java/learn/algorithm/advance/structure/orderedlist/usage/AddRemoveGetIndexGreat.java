package learn.algorithm.advance.structure.orderedlist.usage;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 设计一个顺序表，使得查找某个位置元素、删除某个位置元素，在某个位置增加元素的时间复杂度为 O(logN)
 */
public class AddRemoveGetIndexGreat {

    static class SBTNode<V> {
        public V value;
        public SBTNode<V> l;
        public SBTNode<V> r;
        public int size;

        public SBTNode(V v) {
            value = v;
            size = 1;
        }
    }

    public static class SBTList<V> {

        private SBTNode<V> root;

        public void add(int index, V num) {
            SBTNode<V> cur = new SBTNode<>(num);
            if (root == null) {
                root = cur;
            } else {
                if (index <= root.size) {
                    root = add(root, index, cur);
                }
            }
        }

        public V get(int index) {
            SBTNode<V> ans = get(root, index);
            return ans.value;
        }

        public void remove(int index) {
            if (index >= 0 && size() > index) {
                root = remove(root, index);
            }
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        private SBTNode<V> add(SBTNode<V> root, int index, SBTNode<V> cur) {
            if (root == null) {
                return cur;
            }
            root.size++;
            int leftAndHeadSize = (root.l != null ? root.l.size : 0) + 1;
            if (index < leftAndHeadSize) {
                root.l = add(root.l, index, cur);
            } else {
                root.r = add(root.r, index - leftAndHeadSize, cur);
            }
            root = maintain(root);
            return root;
        }

        private SBTNode<V> remove(SBTNode<V> root, int index) {
            root.size--;
            int rootIndex = root.l != null ? root.l.size : 0;
            if (index != rootIndex) {
                if (index < rootIndex) {
                    root.l = remove(root.l, index);
                } else {
                    root.r = remove(root.r, index - rootIndex - 1);
                }
                return root;
            }
            if (root.l == null && root.r == null) {
                return null;
            }
            if (root.l == null) {
                return root.r;
            }
            if (root.r == null) {
                return root.l;
            }
            SBTNode<V> pre = null;
            SBTNode<V> suc = root.r;
            suc.size--;
            while (suc.l != null) {
                pre = suc;
                suc = suc.l;
                suc.size--;
            }
            if (pre != null) {
                pre.l = suc.r;
                suc.r = root.r;
            }
            suc.l = root.l;
            suc.size = suc.l.size + (suc.r == null ? 0 : suc.r.size) + 1;
            return suc;
        }

        private SBTNode<V> get(SBTNode<V> root, int index) {
            int leftSize = root.l != null ? root.l.size : 0;
            if (index < leftSize) {
                return get(root.l, index);
            } else if (index == leftSize) {
                return root;
            } else {
                return get(root.r, index - leftSize - 1);
            }
        }

        private SBTNode<V> rightRotate(SBTNode<V> cur) {
            SBTNode<V> leftNode = cur.l;
            cur.l = leftNode.r;
            leftNode.r = cur;
            leftNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return leftNode;
        }

        private SBTNode<V> leftRotate(SBTNode<V> cur) {
            SBTNode<V> rightNode = cur.r;
            cur.r = rightNode.l;
            rightNode.l = cur;
            rightNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return rightNode;
        }

        private SBTNode<V> maintain(SBTNode<V> cur) {
            if (cur == null) {
                return null;
            }
            int leftSize = cur.l != null ? cur.l.size : 0;
            int leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            int leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            int rightSize = cur.r != null ? cur.r.size : 0;
            int rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            int rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            if (leftLeftSize > rightSize) {
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }
    }

    public static void main(String[] args) {
        functionalTesting();
        performanceTesting();
    }

    private static void functionalTesting() {
        int testTimes = 50000;
        int max = 1000000;
        ArrayList<Integer> list = new ArrayList<>();
        SBTList<Integer> sbtList = new SBTList<>();
        for (int i = 0; i < testTimes; i++) {
            if (list.size() != sbtList.size()) {
                System.out.println("Oops!");
                break;
            }
            if (list.size() > 1 && Math.random() < 0.5) {
                int removeIndex = (int) (Math.random() * list.size());
                list.remove(removeIndex);
                sbtList.remove(removeIndex);
            } else {
                int randomIndex = (int) (Math.random() * (list.size() + 1));
                int randomValue = (int) (Math.random() * (max + 1));
                list.add(randomIndex, randomValue);
                sbtList.add(randomIndex, randomValue);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(sbtList.get(i))) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("Finish!");
    }

    private static void performanceTesting() {
        int testTimes = 100000;
        int max = 1000000;

        ArrayList<Integer> list = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        SBTList<Integer> sbtList = new SBTList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (list.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
        }
        System.out.println("ArrayList 插入总时长(毫秒) ： " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            list.get(randomIndex);
        }
        System.out.println("ArrayList 读取总时长(毫秒) : " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * list.size());
            list.remove(randomIndex);
        }
        System.out.println("ArrayList 删除总时长(毫秒) : " + (System.currentTimeMillis() - start));
        System.out.println();

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (list.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            linkedList.add(randomIndex, randomValue);
        }
        System.out.println("LinkedList 插入总时长(毫秒) ： " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            linkedList.get(randomIndex);
        }
        System.out.println("LinkedList 读取总时长(毫秒) : " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * list.size());
            linkedList.remove(randomIndex);
        }
        System.out.println("LinkedList 删除总时长(毫秒) : " + (System.currentTimeMillis() - start));
        System.out.println();

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (sbtList.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            sbtList.add(randomIndex, randomValue);
        }
        System.out.println("SBTList 插入总时长(毫秒) : " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            sbtList.get(randomIndex);
        }
        System.out.println("SBTList 读取总时长(毫秒) :  " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * sbtList.size());
            sbtList.remove(randomIndex);
        }
        System.out.println("SBTList 删除总时长(毫秒) :  " + (System.currentTimeMillis() - start));
    }
}
