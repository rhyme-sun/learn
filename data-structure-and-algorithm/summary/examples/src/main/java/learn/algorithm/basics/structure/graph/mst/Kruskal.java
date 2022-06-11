package learn.algorithm.basics.structure.graph.mst;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import learn.algorithm.basics.structure.graph.Edge;
import learn.algorithm.basics.structure.graph.Graph;
import learn.algorithm.basics.structure.graph.Node;

/**
 * 最小生成树算法
 */
public class Kruskal {

    /**
     * @param graph 图
     * @return 返回最小生成树的边
     */
    static Set<Edge> kruskalMST(Graph graph) {
        if (graph == null) {
            return null;
        }
        // 将图的所有边按照权值递增排序，这里使用小根堆
        Set<Edge> edges = graph.edges;
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge : edges) {
            queue.offer(edge);
        }
        Set<Edge> edgeSet = new HashSet<>();
        UnionFind unionFind = new UnionFind(graph);
        while (!queue.isEmpty()) {
            Edge cur = queue.poll();
            Node from = cur.from;
            Node to = cur.to;
            // 判断添加该边后是否成环
            boolean isCircle = unionFind.isSameSet(from, to);
            if (!isCircle) {
                edgeSet.add(cur);
                unionFind.union(from, to);
            }
        }
        return edgeSet;
    }

    static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    static class UnionFind {

        private Map<Node, Node> parent;
        private Map<Node, Integer> sizeMap;

        public UnionFind(Graph graph) {
            parent = new HashMap<>();
            sizeMap = new HashMap<>();
            Map<Integer, Node> nodes = graph.nodes;
            for (Node node : nodes.values()) {
                parent.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public void union(Node a, Node b) {
            Node nodeA = find(a);
            Node nodeB = find(b);
            Integer sizeA = sizeMap.get(nodeA);
            Integer sizeB = sizeMap.get(nodeB);
            if (sizeA >= sizeB) {
                parent.put(nodeB, nodeA);
                sizeMap.put(nodeA, sizeA + sizeB);
            } else {
                parent.put(nodeA, nodeB);
                sizeMap.put(nodeB, sizeB + sizeA);
            }
        }

        public boolean isSameSet(Node a, Node b) {
            return find(a) == find(b);
        }

        private Node find(Node a) {

            Stack<Node> stack = new Stack<>();
            while (parent.get(a) != a) {
                stack.push(a);
                a = parent.get(a);
            }
            while (!stack.isEmpty()) {
                Node node = stack.pop();
                parent.put(node, a);
            }
            return a;
        }
    }
}
