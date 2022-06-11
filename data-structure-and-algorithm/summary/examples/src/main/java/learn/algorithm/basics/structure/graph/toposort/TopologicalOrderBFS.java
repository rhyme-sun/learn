package learn.algorithm.basics.structure.graph.toposort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 拓扑排序
 * 题目链接：https://www.lintcode.com/problem/127/
 */
public class TopologicalOrderBFS {

    static List<DirectedGraphNode> topSort(List<DirectedGraphNode> graph) {
        // value 为节点对应的入度
        Map<DirectedGraphNode, Integer> inMap = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            inMap.put(node, 0);
        }
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode next: node.neighbors) {
                inMap.merge(next, 1, Integer::sum);
            }
        }
        Queue<DirectedGraphNode> zeroQueue = new LinkedList<>();
        for (Map.Entry<DirectedGraphNode, Integer> entry : inMap.entrySet()) {
            if (entry.getValue() == 0) {
                zeroQueue.add(entry.getKey());
            }
        }
        List<DirectedGraphNode> ans = new ArrayList<>();
        while(!zeroQueue.isEmpty()) {
            DirectedGraphNode poll = zeroQueue.poll();
            ans.add(poll);
            for (DirectedGraphNode next: poll.neighbors) {
                int degree = inMap.merge(next, -1, Integer::sum);
                if (degree == 0) {
                    zeroQueue.add(next);
                }
            }
        }
        return ans;
    }

    private static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }
}
