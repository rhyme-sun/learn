package learn.algorithm.practice.p11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 问题一：一个字符串至少要切几刀能让切出来的子串都是回文串；、
 * https://leetcode.cn/problems/palindrome-partitioning-ii/
 * 问题二：返回问题一的其中一种划分结果；
 * 问题三：返回问题一的所有划分结果。
 */
public class Code02_PalindromePartitioningII {

    static int minCut(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        // help[i][j] 表示 [i,j] 范围内子串是否为回文串
        boolean[][] help = help(str);
        // dp[i] 表示以 i 开头子串切分成全部为回文子串的最少刀数
        int[] dp = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            if (help[i][n - 1]) {
                dp[i] = 0;
            } else {
                int min = Integer.MAX_VALUE;
                for (int j = i; j < n; j++) {
                    if (help[i][j]) {
                        min = Math.min(min, dp[j + 1]);
                    }
                }
                dp[i] = 1 + min;
            }
        }
        return dp[0];
    }

    private static boolean[][] help(char[] str) {
        int n = str.length;
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = true;
            dp[i][i + 1] = str[i] == str[i + 1];
        }
        dp[n - 1][n - 1] = true;
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                dp[i][j] = str[i] == str[j] && dp[i + 1][j - 1];
            }
        }
        return dp;
    }

    // 本题第二问，返回其中一种结果
    static List<String> minCutOneWay(String s) {
        if (s == null || s.length() < 2) {
            return Collections.singletonList(s);
        }
        char[] str = s.toCharArray();
        int N = str.length;
        boolean[][] checkMap = help(str);
        int[] dp = new int[N + 1];
        dp[N] = 0;
        for (int i = N - 1; i >= 0; i--) {
            if (checkMap[i][N - 1]) {
                dp[i] = 1;
            } else {
                int next = Integer.MAX_VALUE;
                for (int j = i; j < N; j++) {
                    if (checkMap[i][j]) {
                        next = Math.min(next, dp[j + 1]);
                    }
                }
                dp[i] = 1 + next;
            }
        }
        List<String> ans = new ArrayList<>(dp[0] + 1);
        for (int i = 0, j = 1; j <= N; j++) {
            if (dp[i] == dp[j] + 1) {
                ans.add(s.substring(i, j));
                i = j;
            }
        }
        return ans;
    }

    // 本题第三问，返回所有结果
    static List<List<String>> minCutAllWays(String s) {
        List<List<String>> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            List<String> cur = new ArrayList<>();
            cur.add(s);
            ans.add(cur);
        } else {
            char[] str = s.toCharArray();
            int N = str.length;
            boolean[][] checkMap = help(str);
            int[] dp = new int[N + 1];
            dp[N] = 0;
            for (int i = N - 1; i >= 0; i--) {
                if (checkMap[i][N - 1]) {
                    dp[i] = 1;
                } else {
                    int next = Integer.MAX_VALUE;
                    for (int j = i; j < N; j++) {
                        if (checkMap[i][j]) {
                            next = Math.min(next, dp[j + 1]);
                        }
                    }
                    dp[i] = 1 + next;
                }
            }
            process(s, 0, 1, checkMap, dp, new ArrayList<>(), ans);
        }
        return ans;
    }

    // s[0....i-1] 分出来的结果已经存到 path 里去了
    // s[i..j-1] 考察的分出来的第一份
    // i 可以理解上个切分位置，从 i 开始找到下个切分位置，将之间的字符串收集起来
    private static void process(String s, int i, int j, boolean[][] checkMap, int[] dp,
                                List<String> path,
                                List<List<String>> ans) {
        if (j == s.length()) { // s[i...N-1]
            if (dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));
                ans.add(copyStringList(path));
                path.remove(path.size() - 1);
            }
        } else {// s[i...j-1]
            // 找到了一个切分位置
            if (dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));
                process(s, j, j + 1, checkMap, dp, path, ans);
                path.remove(path.size() - 1);
            }
            // 继续找其他满足条件的切分位置
            process(s, i, j + 1, checkMap, dp, path, ans);
        }
    }

    private static List<String> copyStringList(List<String> list) {
        List<String> ans = new ArrayList<>();
        for (String str : list) {
            ans.add(str);
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = null;
        List<String> ans2 = null;
        List<List<String>> ans3 = null;

        System.out.println("本题第二问，返回其中一种结果测试开始");
        s = "abacbc";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();

        s = "aabccbac";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();

        s = "aabaa";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();
        System.out.println("本题第三问，返回所有可能结果测试开始");

        s = "cbbbcbc";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();

        s = "aaaaaa";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();

        s = "fcfffcffcc";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("本题第三问，返回所有可能结果测试结束");
    }
}
