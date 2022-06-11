package learn.algorithm.basics.structure.unionfind.usage;

/**
 * 朋友圈问题或省份问题，问题描述见：https://leetcode-cn.com/problems/number-of-provinces/
 */
public class Code01_FriendCircles {

    static int findCircleNum(int[][] m) {
        int n = m.length;
        UnionFind unionFind = new UnionFind(n);
        // 遍历矩阵的右上半区（矩阵的行和列表示人的编号，比如 i=0，j=0，都表示 0 号人）
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
        private int[] parent;
        private int[] size;
        private int[] help;
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
