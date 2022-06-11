// 本题测试链接:
// https://lightoj.com/problem/internet-bandwidth
// 这是一道DinicAlgorithm算法的题
// 把如下代码粘贴进网页所提供的java编译器环境中
// 不需要修改任何内容可以直接通过
// 请看网页上的题目描述并结合main函数的写法去了解这个模板的用法

package learn.algorithm.basics.structure.graph.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Dinic 算法，最大网络负载
 */
public class DinicAlgorithm {

    /**
     * 边结构
     */
    static class Edge {
        /**
         * from 节点编号
         */
        public int from;
        /**
         * to 节点编号
         */
        public int to;
        /**
         * 路径还剩于的负载
         */
        public int available;

        public Edge(int a, int b, int c) {
            from = a;
            to = b;
            available = c;
        }
    }

    static class Dinic {
        /**
         * 记录节点的数量
         */
        private int N;
        /**
         * 记录每个节点到下级别节点的路径编号
         */
        private ArrayList<ArrayList<Integer>> nexts;
        /**
         * 路径数组，路径和反向路径相邻存放
         */
        private ArrayList<Edge> edges;
        /**
         * 记录每个节点的深度
         */
        private int[] depth;
        /**
         * 记录每个节点到上次经过的下级路径
         */
        private int[] cur;

        public Dinic(int nums) {
            N = nums + 1;
            nexts = new ArrayList<>();
            for (int i = 0; i <= N; i++) {
                nexts.add(new ArrayList<>());
            }
            edges = new ArrayList<>();
            depth = new int[N];
            cur = new int[N];
        }

        public void addEdge(int u, int v, int r) {
            int m = edges.size();
            edges.add(new Edge(u, v, r));
            nexts.get(u).add(m);
            // 添加反向边
            edges.add(new Edge(v, u, 0));
            nexts.get(v).add(m + 1);
        }

        /**
         * 获取图的最大负载
         */
        public int maxFlow(int s, int t) {
            int flow = 0;
            while (bfs(s, t)) {
                Arrays.fill(cur, 0);
                flow += dfs(s, t, Integer.MAX_VALUE);
                Arrays.fill(depth, 0);
            }
            return flow;
        }

        /**
         * 宽度优先遍历，记录每个节点的深度，
         *
         * @return 由 s 节点能不能到 t
         */
        private boolean bfs(int s, int t) {
            LinkedList<Integer> queue = new LinkedList<>();
            queue.addFirst(s);
            boolean[] visited = new boolean[N];
            visited[s] = true;
            while (!queue.isEmpty()) {
                int u = queue.pollLast();
                for (int i = 0; i < nexts.get(u).size(); i++) {
                    Edge e = edges.get(nexts.get(u).get(i));
                    int v = e.to;
                    if (!visited[v] && e.available > 0) {
                        visited[v] = true;
                        depth[v] = depth[u] + 1;
                        if (v == t) {
                            break;
                        }
                        queue.addFirst(v);
                    }
                }
            }
            return visited[t];
        }

        /**
         * 深度优先遍历
         * 当前来到了 s 点，s 可变，最终目标是 t，t 是固定参数
         * 收集到的负载
         */
        private int dfs(int s, int t, int r) {
            if (s == t || r == 0) {
                return r;
            }
            int f = 0;
            int flow = 0;
            // s 点从哪条边开始试 -> cur[s]
            for (; cur[s] < nexts.get(s).size(); cur[s]++) {
                int ei = nexts.get(s).get(cur[s]);
                Edge e = edges.get(ei);
                Edge o = edges.get(ei ^ 1);
                if (depth[e.to] == depth[s] + 1 && (f = dfs(e.to, t, Math.min(e.available, r))) != 0) {
                    e.available -= f;
                    o.available += f;
                    flow += f;
                    r -= f;
                    if (r <= 0) {
                        break;
                    }
                }
            }
            return flow;
        }
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int cases = cin.nextInt();
        for (int i = 1; i <= cases; i++) {
            int n = cin.nextInt();
            int s = cin.nextInt();
            int t = cin.nextInt();
            int m = cin.nextInt();
            Dinic dinic = new Dinic(n);
            for (int j = 0; j < m; j++) {
                int from = cin.nextInt();
                int to = cin.nextInt();
                int weight = cin.nextInt();
                dinic.addEdge(from, to, weight);
                dinic.addEdge(to, from, weight);
            }
            int ans = dinic.maxFlow(s, t);
            System.out.println("Case " + i + ": " + ans);
        }
        cin.close();
    }
}
