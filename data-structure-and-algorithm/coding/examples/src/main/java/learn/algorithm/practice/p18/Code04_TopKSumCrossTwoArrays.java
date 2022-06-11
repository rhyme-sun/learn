package learn.algorithm.practice.p18;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 给定两个有序数组 arr1 和 arr2，再给定一个整数k，返回来自 arr1 和 arr2 的两个数相加和最大的前 k 个，两个数必须分别来自两个数组，
 * 按照降序输出，时间复杂度为 `O(klogk)`。
 * 输入描述：
 * 第一行三个整数 N, K分别表示数组 arr1，arr2 的大小，以及需要询问的数；
 * 接下来一行 N个整数，表示 arr1 内的元素；
 * 再接下来一行 N 个整数，表示 arr2 内的元素；
 * 输出描述：
 * 输出 K 个整数表示答案
 * 牛客网题目：https://www.nowcoder.com/practice/7201cacf73e7495aa5f88b223bbbf6d1
 */
public class Code04_TopKSumCrossTwoArrays {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] arr1 = new int[N];
        int[] arr2 = new int[N];
        for (int i = 0; i < N; i++) {
            arr1[i] = sc.nextInt();
        }
        for (int i = 0; i < N; i++) {
            arr2[i] = sc.nextInt();
        }
        int[] topK = topKSum(arr1, arr2, K);
        for (int i = 0; i < K; i++) {
            System.out.print(topK[i] + " ");
        }
        System.out.println();
        sc.close();
    }

    // 放入大根堆中的结构
    private static class Node {
        public int i;      // arr1中的位置
        public int j; // arr2中的位置
        public int sum;    // arr1[i] + arr2[j] 的值

        public Node(int i1, int i2, int s) {
            i = i1;
            j = i2;
            sum = s;
        }
    }

    static int[] topKSum(int[] arr1, int[] arr2, int k) {
        if (arr1 == null || arr2 == null || k < 1) {
            return null;
        }
        int n = arr1.length;
        int m = arr2.length;
        k = Math.min(k, n * m);
        int[] ans = new int[k];
        int index = 0;

        PriorityQueue<Node> maxHeap = new PriorityQueue<>(Comparator.comparing((Node node) -> -node.sum));
        HashSet<Long> set = new HashSet<>();
        int i = n - 1;
        int j = m - 1;
        maxHeap.add(new Node(i, j, arr1[i] + arr2[j]));
        set.add(x(i, j, m));

        while (index != k) {
            Node poll = maxHeap.poll();
            ans[index++] = poll.sum;
            i = poll.i;
            j = poll.j;
            set.remove(x(i, j, m));
            if (i - 1 >= 0 && !set.contains(x(i - 1, j, m))) {
                set.add(x(i - 1, j, m));
                maxHeap.add(new Node(i - 1, j, arr1[i - 1] + arr2[j]));
            }
            if (j - 1 >= 0 && !set.contains(x(i, j - 1, m))) {
                set.add(x(i, j - 1, m));
                maxHeap.add(new Node(i, j - 1, arr1[i] + arr2[j - 1]));
            }
        }
        return ans;
    }

    private static long x(int i1, int i2, int M) {
        return (long) i1 * (long) M + (long) i2;
    }
}
