package learn.algorithm.coding.practice.p04;

import java.util.ArrayList;
import java.util.HashMap;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 数组为 {3, 2, 2, 3, 1}，给出一个查询结果 (0, 3, 2)，意思是在数组里下标 0~3 这个范围上，有几个 2，这里答案返回 2。
 * 假设给你一个数组 arr，现在给出的参数有数组查询范围 `[l,r]` 和要查询出现次数的值 v，返回 v 出现的次数。设计一种结果，支持高效的查询。
 */
public class Code01_QueryHobby {

    public static class QueryBox2 {
        private HashMap<Integer, ArrayList<Integer>> map;

        public QueryBox2(int[] arr) {
            map = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                if (!map.containsKey(arr[i])) {
                    map.put(arr[i], new ArrayList<>());
                }
                map.get(arr[i]).add(i);
            }
        }

        public int query(int L, int R, int value) {
            if (!map.containsKey(value)) {
                return 0;
            }
            ArrayList<Integer> indexArr = map.get(value);
            // 查询 < L 的下标有几个
            int a = countLess(indexArr, L);
            // 查询 < R+1 的下标有几个
            int b = countLess(indexArr, R + 1);
            return b - a;
        }

        // pos 中小于 limit 的数有几个
        private int countLess(ArrayList<Integer> arr, int limit) {
            int L = 0;
            int R = arr.size() - 1;
            int mostRight = -1;
            while (L <= R) {
                int mid = L + ((R - L) >> 1);
                // 注意，这里不能有等号
                if (arr.get(mid) < limit) {
                    mostRight = mid;
                    L = mid + 1;
                } else {
                    R = mid - 1;
                }
            }
            // +1 表示个数，不加表示下标
            return mostRight + 1;
        }
    }

    public static class QueryBox1 {
        private int[] arr;

        public QueryBox1(int[] array) {
            arr = new int[array.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = array[i];
            }
        }

        public int query(int L, int R, int v) {
            if (arr.length == 0) {
                return 0;
            }
            int ans = 0;
            for (; L <= R; L++) {
                if (arr[L] == v) {
                    ans++;
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        int len = 300;
        int value = 20;
        int testTimes = 1000;
        int queryTimes = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generateRandomArray(len, value);
            int N = arr.length;
            QueryBox1 box1 = new QueryBox1(arr);
            QueryBox2 box2 = new QueryBox2(arr);
            for (int j = 0; j < queryTimes; j++) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                int L = Math.min(a, b);
                int R = Math.max(a, b);
                int v = (int) (Math.random() * value) + 1;
                int ans1 = box1.query(L, R, v);
                int ans2 = box2.query(L, R, v);
                if (ans1 != ans2) {
                    System.out.println("Oops!");
                    ArrayComparator.printArray(arr);
                    System.out.println(ans1);
                    System.out.println(ans2);
                    break;
                }
            }
        }
        System.out.println("Finish!");
    }
}
