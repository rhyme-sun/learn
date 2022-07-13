package learn.algorithm.coding.practice.p06;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * <p>
 * 给你一个由非负整数组成的数组 nums 。另有一个查询数组 queries ，其中 `queries[i] = [xi, mi]` 。
 * 第 i 个查询的答案是 xi 和任何 nums 数组中不超过 mi 的元素按位异或（XOR）得到的最大值 。如果 nums 中的所有元素都大于 mi，最终答案就是 -1。
 * 返回一个整数数组 answer 作为查询的答案，其中 `answer.length == queries.length` 且 `answer[i]` 是第 i 个查询的答案。
 * <p>
 * leetcode: https://leetcode.cn/problems/maximum-xor-with-an-element-from-array/
 */
public class Code03_MaximumXorWithAnElementFromArray {

    static int[] maximizeXor(int[] nums, int[][] queries) {
        if (nums == null || nums.length == 0 || queries == null || queries.length == 0) {
            return null;
        }
        int[] ans = new int[queries.length];
        NumTrie trie = new NumTrie();
        for (int num : nums) {
            trie.add(num);
        }
        for (int i = 0; i < queries.length; i++) {
            ans[i] = trie.maxXor(queries[i][0], queries[i][1]);
        }
        return ans;
    }

    private static class Node {
        private int min;
        private Node[] nexts;
        Node() {
            min = Integer.MAX_VALUE;
            nexts = new Node[2];
        }
    }

    private static class NumTrie {
        private Node root;
        NumTrie() {
            root = new Node();
        }

        public void add(int num) {
            Node cur = root;
            cur.min = Math.min(cur.min, num);
            for (int move = 30; move >= 0; move--) {
                int path = (num >> move) & 1;
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                cur = cur.nexts[path];
                cur.min = Math.min(cur.min, num);
            }
        }
        public int maxXor(int num, int m) {
            if (root.min > m) {
                return -1;
            }
            Node cur = root;
            int ans = 0;
            for (int move = 30; move >= 0; move--) {
                int path = (num >> move) & 1;
                // 期待的路径（path 为 1，best 选择 0，path 0，best 选择 1，异或值最大）
                int best = path ^ 1;
                best ^= (cur.nexts[best] == null || cur.nexts[best].min > m) ? 1 : 0;
                ans |= (path ^ best) << move;
                cur = cur.nexts[best];
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 3, 4};
        int[][] queries = {{3, 1},{1, 3},{5, 6}};
        int[] ans = maximizeXor(nums, queries);
        ArrayComparator.printArray(ans);
    }
}
