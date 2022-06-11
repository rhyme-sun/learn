package learn.algorithm.basics.structure.trie.usage;

import java.util.Scanner;

/**
 * 题目描述如下：
 * 给定一棵 n 个点的带权树，结点下标从 1 开始到 n。寻找树中找两个结点，求最长的异或路径。
 * 异或路径指的是指两个结点之间唯一路径上的所有边权的异或。
 * <p>
 * https://www.luogu.com.cn/problem/P4551
 */
public class Code02_MaxXorPath {

//    public static void main(String[] args) {
//        int n = 4;
//        int[][] tree = {{1, 2, 3}, {2, 3, 4}, {2, 4, 6}};
//
//        int ans = maxXorPath(n, tree);
//        System.out.println(ans);
//    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] rootU = new int[n + 1];
        int[] father = new int[n + 1];
        father[1] = 1;
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            rootU[v] = rootU[u] ^ w;
            father[v] = u;
        }
        int ans = maxXorPath(n, rootU);
        System.out.println(ans);
    }

    static int maxXorPath(int n, int[] rootU) {
        Trie trie = new Trie();
        for (int rootUXor : rootU) {
            trie.insert(rootUXor);
        }
        int ans = 0;
        for (int rootUXor : rootU) {
            int rootVXor = trie.maxXor(rootUXor);
            ans = Math.max(ans, rootUXor ^ rootVXor);
        }
        return ans;
    }

    static int maxXorPath(int n, int[][] tree) {
        // T(root,u) 表示 root->u 路径权值异或和
        // 求全部的 T(root,u)
        // rootU[i] 表示 1~i 异或和
        int[] rootU = buildRootUXor(n, tree);
        Trie trie = new Trie();
        for (int rootUXor : rootU) {
            trie.insert(rootUXor);
        }
        int ans = 0;
        for (int rootUXor : rootU) {
            int rootVXor = trie.maxXor(rootUXor);
            ans = Math.max(ans, rootUXor ^ rootVXor);
        }
        return ans;
    }

    private static int[] buildRootUXor(int n, int[][] tree) {
        int[] rootU = new int[n + 1];
        // 记录每个节点的父节点
        // father[i] 表示 i 的父节点
        int[] father = new int[n + 1];
        father[1] = 1;
        // 1 2 3
        // 2 3 4
        // 2 4 6
        for (int i = 0; i < tree.length; i++) {
            int[] path = tree[i];
            int u = path[0];
            int v = path[1];
            int w = path[2];
            rootU[v] = rootU[u] ^ w;
            father[v] = u;
        }
        return rootU;
    }

    private static class Trie {
        Node root;

        Trie() {
            root = new Node();
        }

        public void insert(int rootUXor) {
            // 从高位到低位，取得每个位上的值
            // 1110
            Node cur = root;
            for (int move = 30; move >= 0; move--) {
                int path = (rootUXor >> move) & 1;
                if (cur.next[path] == null) {
                    cur.next[path] = new Node();
                }
                cur = cur.next[path];
            }
            cur.rootUXor = rootUXor;
        }

        public int maxXor(int rootUXor) {
            Node cur = root;
            for (int move = 30; move >= 0; move--) {
                int path = (rootUXor >> move) & 1;
                if (cur.next[path ^ 1] == null) {
                    cur = cur.next[path];
                } else {
                    cur = cur.next[path ^ 1];
                }
            }
            return cur.rootUXor;
        }

        private static class Node {
            Node[] next;
            int rootUXor;

            public Node() {
                next = new Node[2];
            }
        }
    }
}
