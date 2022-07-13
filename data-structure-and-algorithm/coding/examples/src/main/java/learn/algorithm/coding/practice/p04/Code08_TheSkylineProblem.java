package learn.algorithm.coding.practice.p04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 题目描述如下：
 * <p>
 * 城市的 天际线 是从远处观看该城市中所有建筑物形成的轮廓的外部轮廓。给你所有建筑物的位置和高度，请返回 由这些建筑物形成的 天际线 。
 * 每个建筑物的几何信息由数组 buildings 表示，其中三元组 `buildings[i] = [lefti, righti, heighti]` 表示：
 * <p>
 * `lefti` 是第 i 座建筑物左边缘的 x 坐标。
 * `righti` 是第 i 座建筑物右边缘的 x 坐标。
 * `heighti` 是第 i 座建筑物的高度。
 * 你可以假设所有的建筑都是完美的长方形，在高度为 0 的绝对平坦的表面上。
 * <p>
 * leetcode: https://leetcode.cn/problems/the-skyline-problem/
 */
public class Code08_TheSkylineProblem {

    static List<List<Integer>> getSkyLine(int[][] matrix) {
        Node[] nodes = findAllChangePos(matrix);
        Arrays.sort(nodes, new NodeComparator());

        // key 为高度，value 为高度出现的次数，该有序表是为了能够快速拿到某个位置的最大高度
        TreeMap<Integer, Integer> maxH = new TreeMap<>();
        // 每个位置的最大高度
        TreeMap<Integer, Integer> xMaxH = new TreeMap<>();

        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            if (node.isAdd) {
                if (maxH.containsKey(node.h)) {
                    maxH.put(node.h, maxH.get(node.h) + 1);
                } else {
                    maxH.put(node.h, 1);
                }
            } else {
                Integer times = maxH.get(node.h);
                if (--times == 0) {
                    maxH.remove(node.h);
                } else {
                    maxH.put(node.h, times);
                }
            }
            // x 位置最大高度
            if (maxH.isEmpty()) {
                xMaxH.put(nodes[i].x, 0);
            } else {
                xMaxH.put(nodes[i].x, maxH.lastKey());
            }
        }

        List<List<Integer>> ans = new ArrayList<>();
        for (Entry<Integer, Integer> entry : xMaxH.entrySet()) {
            int curX = entry.getKey();
            int curMaxHeight = entry.getValue();
            if (ans.isEmpty() || ans.get(ans.size() - 1).get(1) != curMaxHeight) {
                ans.add(new ArrayList<>(Arrays.asList(curX, curMaxHeight)));
            }
        }
        return ans;
    }

    /**
     * 找到最大高度发生变换的可能位置
     * matrix[i][0] i 号大楼起点
     * matrix[i][1] i 号大楼终点
     * matrix[i][2] i 号大楼高度
     */
    private static Node[] findAllChangePos(int[][] matrix) {
        Node[] nodes = new Node[matrix.length * 2];
        for (int i = 0; i < matrix.length; i++) {
            nodes[2 * i] = new Node(matrix[i][0], true, matrix[i][2]);
            nodes[i * 2 + 1] = new Node(matrix[i][1], false, matrix[i][2]);
        }
        return nodes;
    }

    static class Node {
        public int x;
        /**
         * true 表示增加高度。false 表示减少高度
         */
        public boolean isAdd;
        public int h;

        public Node(int x, boolean isAdd, int h) {
            this.x = x;
            this.isAdd = isAdd;
            this.h = h;
        }
    }

    static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.x - o2.x;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{0,2,3}, {2,5,3}};
        List<List<Integer>> skyLine1 = getSkyLine(matrix);
        System.out.println(skyLine1);
    }
}
