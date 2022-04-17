package learn.algorithm.unionfind;

/**
 * 朋友圈问题或省份问题，问题描述见：https://leetcode-cn.com/problems/number-of-provinces/
 */
public class FriendCircles {

    static int findCircleNum(int[][] m) {
        int n = m.length;
        UnionFind unionFind = new UnionFind(n);
        // 遍历矩阵的右上半区
        // 0 1~n-1
        // 1 2~n-1
        // 2 3~n-1
        // ...
        // n-2 n-1
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (m[i][j] == 1) { // i 和 j 互相认识
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets();
    }

    /**
     * 基于数组实现的并查集
     */
    static class UnionFind {
        /**
         * 父级数组，parent[i]=k，表示 i 的父级为 k
         */
        private int[] parent;
        /**
         * i 作为代表节点集合的数量
         * 如果i是代表节点，size[i] 才有意义，否则无意义
         */
        private int[] size;
        /**
         * 辅助结构，用来辅助路径压缩
         */
        private int[] help;
        /**
         * 并查集内集合数量
         */
        private int sets;

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            help = new int[n];
            sets = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        /**
         * 合并 i 和 j 所在集合
         */
        public void union(int i, int j) {
            int f1 = find(i);
            int f2 = find(j);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        /**
         * 查找代表节点，并进行路径压缩
         */
        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        public int sets() {
            return sets;
        }
    }
}
