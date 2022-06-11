package learn.algorithm.practice.p14;

public class Code01_Parentheses {

    // s只由(和)组成
    // 求最长有效括号子串长度
    // 本题测试链接 : https://leetcode.com/problems/longest-valid-parentheses/
    static int longestValidParentheses(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        char[] str = s.toCharArray();
        int[] dp = new int[str.length];
        int ans = 0;
        for (int i = 1; i < str.length; i++) {
            if (str[i] == ')') {
                int pre = i - dp[i - 1] - 1;
                if (pre >= 0 && str[pre] == '(') {
                    dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    // 贪心解
    static int longestValidParentheses2(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        int ans = 0;
        char[] str = s.toCharArray();
        int n = str.length;

        int count = 0;
        int pre = 0;
        for (int i = 0; i < n; i++) {
            if (str[i] == '(') {
                count++;
            } else {
                count--;
                if (count < 0) {
                    ans = Math.max(ans, i - pre);
                    pre = i + 1;
                    count = 0;
                } else if (count == 0) {
                    ans = Math.max(ans, i - pre + 1);
                }
            }
        }
        count = 0;
        pre = n - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (str[i] == ')') {
                count++;
            } else {
                count--;
                if (count < 0) {
                    ans = Math.max(ans, pre - i);
                    pre = i - 1;
                    count = 0;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String str = "(()";
        int ans = longestValidParentheses2(str);
        System.out.println(ans);
    }
}
