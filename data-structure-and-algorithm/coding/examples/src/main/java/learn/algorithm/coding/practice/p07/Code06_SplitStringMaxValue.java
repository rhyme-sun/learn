package learn.algorithm.coding.practice.p07;

import java.util.HashMap;

/**
 * 题目描述如下：
 * 给了四个参数，String str、int k、String[] parts、int[] record。
 * <p>
 * str 表示样本字符串，k 表示样本字符串要拆分的个数，parts 数组表示词典表，str 拆分的结构必须在 parts 里，record 表示每个部分的得分。
 * 请问 str 切成 k 个部分，最大得分是多少？
 */
public class Code06_SplitStringMaxValue {

    /**
     * 递归尝试
     */
    static int maxRecord1(String str, int K, String[] parts, int[] record) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        // key 词典表里的字符串，value 为对应分数
        HashMap<String, Integer> records = new HashMap<>();
        for (int i = 0; i < parts.length; i++) {
            records.put(parts[i], record[i]);
        }
        return process(str.toCharArray(), 0, K, records);
    }

    /**
     * 当前来到 index 位置，还剩 rest 个字符串需要切割，返回最大切割分数
     */
    private static int process(char[] str, int index, int rest, HashMap<String, Integer> records) {
        if (rest < 0) {
            // -1 表示无效值
            return -1;
        }
        if (index == str.length) {
            return rest == 0 ? 0 : -1;
        }
        int ans = -1;
        StringBuilder pre = new StringBuilder();
        for (int end = index; end < str.length; end++) {
            pre.append(str[end]);
            int next = records.containsKey(pre.toString()) ? process(str, end + 1, rest - 1, records) : -1;
            if (next != -1) {
                ans = Math.max(ans, records.get(pre.toString()) + next);
            }
        }
        return ans;
    }

    /**
     * 严格递推优化
     */
    private static int maxRecord2(String str, int k, String[] parts, int[] record) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        HashMap<String, Integer> records = new HashMap<>();
        for (int i = 0; i < parts.length; i++) {
            records.put(parts[i], record[i]);
        }
        char[] chars = str.toCharArray();
        int n = chars.length;
        int[][] dp = new int[n + 1][k + 1];
        for (int rest = 1; rest <= k; rest++) {
            dp[n][rest] = -1;
        }
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= k; rest++) {
                int ans = -1;
                StringBuilder pre = new StringBuilder();
                for (int end = index; end < n; end++) {
                    pre.append(chars[end]);
                    int next = rest > 0 && records.containsKey(pre.toString()) ? dp[end + 1][rest - 1] : -1;
                    if (next != -1) {
                        ans = Math.max(ans, records.get(pre.toString()) + next);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][k];
    }

    // 动态规划解 + 前缀树优化
    static int maxRecord3(String s, int k, String[] parts, int[] record) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        TrieNode root = rootNode(parts, record);
        char[] str = s.toCharArray();
        int n = str.length;
        int[][] dp = new int[n + 1][k + 1];
        for (int rest = 1; rest <= k; rest++) {
            dp[n][rest] = -1;
        }
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= k; rest++) {
                int ans = -1;
                TrieNode cur = root;
                for (int end = index; end < n; end++) {
                    int path = str[end] - 'a';
                    if (cur.nexts[path] == null) {
                        break;
                    }
                    cur = cur.nexts[path];
                    int next = rest > 0 && cur.value != -1 ? dp[end + 1][rest - 1] : -1;
                    if (next != -1) {
                        ans = Math.max(ans, cur.value + next);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][k];
    }

    // 构建前缀树，结尾节点存放分数，没有结尾节点分数为 -1
    private static TrieNode rootNode(String[] parts, int[] record) {
        TrieNode root = new TrieNode();
        for (int i = 0; i < parts.length; i++) {
            char[] str = parts[i].toCharArray();
            TrieNode cur = root;
            for (int j = 0; j < str.length; j++) {
                int path = str[j] - 'a';
                if (cur.nexts[path] == null) {
                    cur.nexts[path] = new TrieNode();
                }
                cur = cur.nexts[path];
            }
            cur.value = record[i];
        }
        return root;
    }

    private static class TrieNode {
        public TrieNode[] nexts;
        // 分数
        public int value;

        public TrieNode() {
            nexts = new TrieNode[26];
            value = -1;
        }
    }

    public static void main(String[] args) {
        String str = "abcdefg";
        int K = 3;
        String[] parts = {"abc", "def", "g", "ab", "cd", "efg", "defg"};
        int[] record = {1, 1, 1, 3, 3, 3, 2};
        System.out.println(maxRecord1(str, K, parts, record));
        System.out.println(maxRecord2(str, K, parts, record));
        System.out.println(maxRecord3(str, K, parts, record));
    }
}
