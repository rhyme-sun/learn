package learn.algorithm.coding.practice.p17;

import java.util.Comparator;
import java.util.PriorityQueue;

// 本题测试链接 : https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
public class Code02_KthSmallestElementInSortedMatrix {

    // 堆的方法
    static int kthSmallest1(int[][] matrix, int k) {
        int N = matrix.length;
        int M = matrix[0].length;
        PriorityQueue<Node> heap = new PriorityQueue<>(Comparator.comparing((Node n) -> n.value));
        boolean[][] set = new boolean[N][M];
        heap.add(new Node(matrix[0][0], 0, 0));
        set[0][0] = true;
        int count = 0;
        Node ans = null;
        while (!heap.isEmpty()) {
            ans = heap.poll();
            if (++count == k) {
                break;
            }
            int row = ans.row;
            int col = ans.col;
            if (row + 1 < N && !set[row + 1][col]) {
                heap.add(new Node(matrix[row + 1][col], row + 1, col));
                set[row + 1][col] = true;
            }
            if (col + 1 < M && !set[row][col + 1]) {
                heap.add(new Node(matrix[row][col + 1], row, col + 1));
                set[row][col + 1] = true;
            }
        }
        return ans.value;
    }

    private static class Node {
        // 值
        public int value;
        // 行
        public int row;
        // 列
        public int col;

        public Node(int v, int r, int c) {
            value = v;
            row = r;
            col = c;
        }
    }

    // 二分方法
    static int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int l = matrix[0][0];
        int r = matrix[n - 1][n - 1];

        int ans = Integer.MIN_VALUE;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            int[] info = countLess(matrix, m);
            // 注意这里的答案收集方式
            if (info[0] < k) {
                l = m + 1;
            } else {
                ans = info[1];
                r = m - 1;
            }
        }
        return ans;
    }

    // 统计矩阵中小于等于 num 个数
    // int[0] 矩阵中小于等于 num 个数
    // int[1] 矩阵中比 num 小且最接近 num 的值
    private static int[] countLess(int[][] matrix, int num) {
        // 左下角
        int n = matrix.length;
        int row = n - 1;
        int col = 0;

        int count = 0;
        int value = Integer.MIN_VALUE;
        while (row >= 0 && col < n) {
            if (matrix[row][col] <= num) {
                count += row + 1;
                value = Math.max(value, matrix[row][col]);
                col++;
            } else {
                row--;
            }
        }
        return new int[]{count, value};
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2}, {1, 3}};
        int k = 1;
        int ans = kthSmallest(matrix, k);
        System.out.println(ans);
    }
}
