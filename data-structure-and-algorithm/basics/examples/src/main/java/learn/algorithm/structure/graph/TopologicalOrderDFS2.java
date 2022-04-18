package learn.algorithm.structure.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 题目链接：https://www.lintcode.com/problem/127/
 * 拓扑排序，基于点次（深度优先遍历思想）
 */
public class TopologicalOrderDFS2 {

    /**
     * 图的节点结构
     */
    static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }

    /**
     * 记录节点和节点的点次
     */
    static class Record {
        /**
         * 图节点
         */
        public DirectedGraphNode node;
        /**
         * 图节点的点次
         */
        public long nodes;

        public Record(DirectedGraphNode n, long o) {
            node = n;
            nodes = o;
        }
    }

    static class MyComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1);
        }
    }

    static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            f(cur, order);
        }
        ArrayList<Record> recordArr = new ArrayList<>();
        for (Record r : order.values()) {
            recordArr.add(r);
        }
        recordArr.sort(new MyComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record r : recordArr) {
            ans.add(r.node);
        }
        return ans;
    }

    /**
     * 当前来到 cur点，请返回 cur 点所到之处，所有的点次
     *
     * @param cur
     * @param order 点次缓存，key 为节点，value 节点对于点次
     */
    private static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        long nodes = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            nodes += f(next, order).nodes;
        }
        Record ans = new Record(cur, nodes + 1);
        order.put(cur, ans);
        return ans;
    }
}
