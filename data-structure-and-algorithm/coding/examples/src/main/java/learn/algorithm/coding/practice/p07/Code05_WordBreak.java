package learn.algorithm.coding.practice.p07;

import java.util.HashSet;
import java.util.Set;

/**
 * 假设所有字符都是小写字母. 字符串是 str. arr 是去重的单词表，每个单词都不是空字符串且可以使用任意次。
 * 使用 arr 中的单词有多少种拼接 str 的方式. 返回方法数。
 */

public class Code05_WordBreak {

    /**
     * 方法一，递归尝试版本，时间复杂度 O(N^2)
     */
    static int ways1(String str, String[] arr) {
        if (arr == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        // 字典表
        Set<String> table = new HashSet<>();
        for (String s : arr) {
            table.add(s);
        }
        return process1(str.toCharArray(), 0, table);
    }

    private static int process1(char[] str, int index, Set<String> table) {
        if (index == str.length) {
            return 1;
        }
        int ans = 0;
        StringBuilder pre = new StringBuilder();
        for (int i = index; i < str.length; i++) {
            // index~i 位置组成的前缀字符串是否在 table 中
            pre.append(str[i]);
            if (table.contains(pre.toString())) {
                ans += process1(str, i + 1, table);
            }
        }
        return ans;
    }

    /**
     * 方法一的严格递推优化
     */
    static int ways2(String str, String[] arr) {
        if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        Set<String> table = new HashSet<>();
        for (String s : arr) {
            table.add(s);
        }
        char[] chars = str.toCharArray();
        int n = str.length();
        // dp[i] 表示从 i 往后的字符组成的字符串能够拆分为词典表中的字符串的拆分方法数
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            StringBuilder pre = new StringBuilder();
            for (int end = i; end < n; end++) {
                pre.append(chars[end]);
                if (table.contains(pre.toString())) {
                    dp[i] += dp[end + 1];
                }
            }
        }
        return dp[0];
    }

    /**
     * 使用前缀树判断 index 往后的前缀字符串是否在词典表里
     */
    static int ways3(String str, String[] arr) {
        if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        // 用词典表构建前缀树
        Node root = new Node();
        for (String s : arr) {
            char[] chs = s.toCharArray();
            Node node = root;
            for (int i = 0; i < chs.length; i++) {
                int index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.end = true;
        }

        return process3(str.toCharArray(), root, 0);
    }

    private static int process3(char[] str, Node root, int i) {
        if (i == str.length) {
            return 1;
        }
        int ways = 0;
        Node cur = root;
        for (int end = i; end < str.length; end++) {
            int path = str[end] - 'a';
            // 如果 i..end 的前缀串不在前缀树种，那么 i 到 end 往后的前缀串肯定也不在前缀树中，可以直接返回，这一点
            // 在利用哈希表判断前缀串是否在词典表中是做不到的
            if (cur.nexts[path] == null) {
                break;
            }
            cur = cur.nexts[path];
            if (cur.end) { // i...end 组成的前缀字符串在词典表里
                ways += process3(str, root, end + 1);
            }
        }
        return ways;
    }

    static int ways4(String s, String[] arr) {
        if (s == null || s.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        Node root = new Node();
        for (String str : arr) {
            char[] chs = str.toCharArray();
            Node node = root;
            int index;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.end = true;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            Node cur = root;
            for (int end = i; end < n; end++) {
                int path = str[end] - 'a';
                if (cur.nexts[path] == null) {
                    break;
                }
                cur = cur.nexts[path];
                if (cur.end) {
                    dp[i] += dp[end + 1];
                }
            }
        }
        return dp[0];
    }

    /**
     * 前缀树节点结构
     */
    private static class Node {
        public boolean end;
        public Node[] nexts;

        public Node() {
            end = false;
            nexts = new Node[26];
        }
    }

    // for test
    private static class RandomSample {
        public String str;
        public String[] arr;

        public RandomSample(String s, String[] a) {
            str = s;
            arr = a;
        }
    }

    // for test
    private static RandomSample generateRandomSample(char[] candidates, int num, int len, int joint) {
        String[] seeds = randomSeeds(candidates, num, len);
        HashSet<String> set = new HashSet<>();
        for (String str : seeds) {
            set.add(str);
        }
        String[] arr = new String[set.size()];
        int index = 0;
        for (String str : set) {
            arr[index++] = str;
        }
        StringBuilder all = new StringBuilder();
        for (int i = 0; i < joint; i++) {
            all.append(arr[(int) (Math.random() * arr.length)]);
        }
        return new RandomSample(all.toString(), arr);
    }

    private static String[] randomSeeds(char[] candidates, int num, int len) {
        String[] arr = new String[(int) (Math.random() * num) + 1];
        for (int i = 0; i < arr.length; i++) {
            char[] str = new char[(int) (Math.random() * len) + 1];
            for (int j = 0; j < str.length; j++) {
                str[j] = candidates[(int) (Math.random() * candidates.length)];
            }
            arr[i] = String.valueOf(str);
        }
        return arr;
    }

    public static void main(String[] args) {
        char[] candidates = {'a', 'b'};
        int num = 20;
        int len = 4;
        int joint = 5;
        int testTimes = 30000;
        for (int i = 0; i < testTimes; i++) {
            RandomSample sample = generateRandomSample(candidates, num, len, joint);
            int ans1 = ways1(sample.str, sample.arr);
            int ans2 = ways2(sample.str, sample.arr);
            int ans3 = ways3(sample.str, sample.arr);
            int ans4 = ways4(sample.str, sample.arr);
            if (ans1 != ans2 || ans3 != ans4 || ans2 != ans4) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
