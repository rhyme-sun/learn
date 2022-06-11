package learn.algorithm.structure.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目链接：https://www.lintcode.com/problem/127/
 * 拓扑排序，基于点次（深度优先遍历思想）
 */
public class TopologicalOrderDFS1 {

    List<DirectedGraphNode> r;
    // 标记图中节点
    Map<DirectedGraphNode, Integer> gmark;

    public List<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        this.r = new ArrayList<>();
        this.gmark = new HashMap<>();
        for (Object obj : graph) {
            DirectedGraphNode g = (DirectedGraphNode) obj;
            this.dfs(g);
        }
        // 反转模拟栈的数组并返回
        Collections.reverse(this.r);
        return this.r;
    }

    private void dfs(DirectedGraphNode s) {
        // 如果节点已经访问过了就直接返回
        if (this.gmark.computeIfAbsent(s, value -> 0) == 1) return ;
        // 节点访问过了标记变为 1
        this.gmark.compute(s, (key, value) -> 1);
        // 如果没有相邻节点直接入栈
        if (s.neighbors.size() == 0) {
            this.r.add(s);
            return;
        }
        for (DirectedGraphNode obj : s.neighbors) {
            this.dfs(obj);
        }
        this.r.add(s);
    }

    /**
     * 图的节点结构
     */
    private static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }
}
