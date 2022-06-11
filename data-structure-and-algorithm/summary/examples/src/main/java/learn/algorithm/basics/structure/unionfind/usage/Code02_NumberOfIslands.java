package learn.algorithm.basics.structure.unionfind.usage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import learn.algorithm.basics.structure.unionfind.UnionFind;
import learn.algorithm.comparator.MatrixComparator;

/**
 * 岛问题，对应 leetcode 题目：https://leetcode.cn/problems/number-of-islands/
 */
public class Code02_NumberOfIslands {

    /**
     * 方法 1：使用并查集（基于 Map）
     */
    static int numIslands1(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }
        UnionFind.UnionFindSet<Dot> uf = new UnionFind.UnionFindSet<>(dotList);
        // 处理第一行，即只有左没有上
        for (int j = 1; j < col; j++) {
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                // 向左合并
                uf.union(dots[0][j - 1], dots[0][j]);
            }
        }
        // 处理第一列，即只有上没有左
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                // 向上合并
                uf.union(dots[i - 1][0], dots[i][0]);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1') {
                        uf.union(dots[i][j - 1], dots[i][j]);
                    }
                    if (board[i - 1][j] == '1') {
                        uf.union(dots[i - 1][j], dots[i][j]);
                    }
                }
            }
        }
        return uf.sets();
    }

    /**
     * 辅助对象，用来区分数组中的不同的 1，好存放到并查集中的 Hash 表中
     */
    static class Dot {

    }

    /**
     * 方法 3，使用并查集（基于数组）
     */
    static int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;

        final UnionFindByArray unionFind = new UnionFindByArray(board);
        // 第一行
        for (int i = 1; i < col; i++) {
            if (board[0][i - 1] == '1' && board[0][i] == '1') {
                // 向左合并
                unionFind.union(0, i - 1, 0, i);
            }
        }
        // 第一列
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                // 向上合并
                unionFind.union(i, 0, i - 1, 0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1') {
                        // 向左合并
                        unionFind.union(i, j - 1, i, j);
                    }
                    if (board[i - 1][j] == '1') {
                        // 向上合并
                        unionFind.union(i - 1, j, i, j);
                    }
                }
            }
        }
        return unionFind.sets;
    }

    static class UnionFindByArray {
        /**
         * 父级数组
         */
        private int[] parent;
        /**
         * i 作为代表节点集合数量
         */
        private int[] size;
        /**
         * 辅助数组
         */
        private int[] help;
        /**
         * 矩阵列数
         */
        private int col;
        /**
         * 并查集内集合数量
         */
        private int sets;

        public UnionFindByArray(char[][] board) {
            int row = board.length;
            col = board[0].length;
            int n = row * col;
            parent = new int[n];
            size = new int[n];
            help = new int[n];

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == '1') {
                        int index = index(i, j);
                        parent[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }
        }

        public void union(int r1, int c1, int r2, int c2) {
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);

            final int f1 = find(i1);
            final int f2 = find(i2);
            if (f1 == f2) {
                return;
            }
            if (size[f1] >= size[f2]) {
                parent[f2] = f1;
                size[f1] += size[f2];
            } else {
                parent[f1] = f2;
                size[f2] += size[f1];
            }
            sets--;
        }

        /**
         * 找代表节点，并进行路径压缩
         */
        private int find(int i) {
            int hi = 0;
            while (parent[i] != i) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        /**
         * 求矩阵中元素下标
         *
         * @param i 行号
         * @param j 列号
         * @return 矩阵元素从左到右，从上到下编号（从 0 开始）
         */
        private int index(int i, int j) {
            return i * this.col + j;
        }

        public int sets() {
            return sets;
        }
    }

    /**
     * 感染方法，深度优先遍历
     */
    static int numIslands3(char[][] board) {
        if (board == null || board.length == 0) {
            return 0;
        }
        int num = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '1') {
                    infect(board, i, j);
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * 感染操作，从 (i,j) 这个位置出发，把所有连成一片的 '1' 字符，变成数字 0
     */
    private static void infect(char[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[i].length || board[i][j] != '1') {
            return;
        }
        board[i][j] = 0;
        infect(board, i - 1, j);
        infect(board, i + 1, j);
        infect(board, i, j - 1);
        infect(board, i, j + 1);
    }

    private static final int[][] move = {{-1,0}, {1,0}, {0,-1}, {0,1}};

    static int numIslands4(char[][] board) {
        if (board == null) {
            return 0;
        }
        // 逐层扩散，看每层边界中是否有 1，有则联通，无则达到了边界
        int n = board.length;
        int m = board[0].length;
        Queue<Node> queue = new LinkedList<>();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '1') {
                    queue.add(new Node(i, j));
                    board[i][j] = '0';
                    while(!queue.isEmpty()) {
                        Node poll = queue.poll();
                        for (int k = 0; k < 4; k++) {
                            int ni = poll.i + move[k][0];
                            int nj = poll.j + move[k][1];
                            if (ni >= 0 && ni < n && nj >=0 && nj < m && board[ni][nj] == '1') {
                                queue.add(new Node(ni, nj));
                                board[ni][nj] = '0';
                            }
                        }
                    }
                    ans++;
                }
            }
        }
        return ans;
    }

    private static class Node {
        int i;
        int j;

        Node(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String[] args) {
        int row;
        int col;
        char[][] board1;
        char[][] board2;
        char[][] board3;
        char[][] board4;
        long start;
        long end;

        row = 1000;
        col = 1000;
        board1 = MatrixComparator.generateOneOrZeroRandomMatrix(row, col);
        board2 = MatrixComparator.copy(board1);
        board3 = MatrixComparator.copy(board1);
        board4 = MatrixComparator.copy(board1);

        System.out.println("感染方法、并查集（Map 实现）、并查集（数组实现）的运行结果和运行时间：");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("深度优先遍历: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("深度优先遍历时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("宽度优先遍历: " + numIslands4(board4));
        end = System.currentTimeMillis();
        System.out.println("宽度优先遍历时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集（Map 实现）的运行结果: " + numIslands1(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集（Map 实现）的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集（数组实现）的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集（数组实现）的运行时间: " + (end - start) + " ms");

        System.out.println();

        row = 10000;
        col = 10000;
        board1 = MatrixComparator.generateOneOrZeroRandomMatrix(row, col);
        board3 = MatrixComparator.copy(board1);
        System.out.println("感染方法、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集（数组实现）的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集（数组实现）的运行时间: " + (end - start) + " ms");
    }
}
