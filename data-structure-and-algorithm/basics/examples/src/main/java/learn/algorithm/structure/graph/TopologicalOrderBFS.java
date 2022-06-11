package learn.algorithm.structure.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 题目链接：https://www.lintcode.com/problem/127/
 */
public class TopologicalOrderBFS {

    static List<DirectedGraphNode> topSort(List<DirectedGraphNode> graph) {
        // 统计节点的入度
        Map<DirectedGraphNode, Integer> inDegreeMap = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            inDegreeMap.put(cur, 0);
        }
        for (DirectedGraphNode cur : graph) {
            for (DirectedGraphNode next : cur.neighbors) {
                inDegreeMap.put(next, inDegreeMap.get(next) + 1);
            }
        }
        // 将入度为 0 的节点放入队列
        Queue<DirectedGraphNode> zeroQueue = new LinkedList<>();
        for (DirectedGraphNode cur : inDegreeMap.keySet()) {
            if (inDegreeMap.get(cur) == 0) {
                zeroQueue.add(cur);
            }
        }

        // 弹出入度为 0 的节点，将新入度为 0 的节点入队
        List<DirectedGraphNode> ans = new ArrayList<>();
        while (!zeroQueue.isEmpty()) {
            DirectedGraphNode cur = zeroQueue.poll();
            ans.add(cur);
            for (DirectedGraphNode next : cur.neighbors) {
                inDegreeMap.put(next, inDegreeMap.get(next) - 1);
                if (inDegreeMap.get(next) == 0) {
                    zeroQueue.offer(next);
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
