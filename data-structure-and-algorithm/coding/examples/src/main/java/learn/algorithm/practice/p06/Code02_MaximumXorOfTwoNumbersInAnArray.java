package learn.algorithm.practice.p06;

import java.util.HashSet;
import java.util.Set;

/**
 * 题目描述如下：
 * 数组中所有数都异或起来的结果，叫做异或和，给定一个数组 arr，想知道 arr 中哪两个数的异或结果最大。返回最大的异或结果。
 * leetcode: https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/
 */
public class Code02_MaximumXorOfTwoNumbersInAnArray {

    static int findMaximumXOR(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        NumTrie numTrie = new NumTrie();
        numTrie.add(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            max = Math.max(max, numTrie.maxXor(arr[i]));
            numTrie.add(arr[i]);
        }
        return max;
    }

    private static class Node {
        public Node[] nexts = new Node[2];
    }

    private static class NumTrie {
        public Node head = new Node();

        public void add(int newNum) {
            Node cur = head;
            for (int move = 31; move >= 0; move--) {
                int path = ((newNum >> move) & 1);
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                cur = cur.nexts[path];
            }
        }

        public int maxXor(int sum) {
            Node cur = head;
            int res = 0;
            for (int move = 31; move >= 0; move--) {
                int path = (sum >> move) & 1;
                int best = move == 31 ? path : (path ^ 1);
                best = cur.nexts[best] != null ? best : (best ^ 1);
                res |= (path ^ best) << move;
                cur = cur.nexts[best];
            }
            return res;
        }
    }

    static int findMaximumXOR2(int[] nums) {
        int mask = 0;
        int res = 0;
        for(int i = 30; i >= 0; i--){
            mask = mask | (1 << i);

            // 仅有 num 的 1~30 为组成的数
            Set<Integer> set = new HashSet<>();
            for(int num : nums) {
                set.add(mask & num);
            }

            //targetMax ^ prefix = exist
            int targetMax = res | (1 << i);
            for(Integer prefix : set){
                if(set.contains(prefix ^ targetMax)){
                    res = targetMax;
                    break;
                }
            }
        }

        return res;
    }
}
