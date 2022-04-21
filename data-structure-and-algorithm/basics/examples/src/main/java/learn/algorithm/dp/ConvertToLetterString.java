package learn.algorithm.dp;

import learn.algorithm.comparator.StringComparator;

/**
 * 题目描述如下：
 * 规定 1 和 A 对应、2 和 B 对应、3 和 C 对于，11 和 K 对应，...，26 和 Z 对应。那么一个数字字符串比如 111 就可以转化为：AAA、KA 和 AK。
 * 给定一个只有数字字符组成的字符串 str，返回有多少种转化结果。
 */
public class ConvertToLetterString {

    /**
     * str 只含有数字字符 0~9，按照规则转换成字母字符串
     *
     * @param str
     * @return 转换方案数量
     */
    static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    /**
     * 来到 i 位置，考虑递归过程
     *
     * @param str str
     * @param i   i
     * @return 转换方案数量
     */
    private static int process(char[] str, int i) {
        if (i == str.length) {
            // 字符使用完毕，表示得到了一种方案
            return 1;
        }
        // 单独处理 0 字符，没有与之对应的字母，之前的决定有无效
        if (str[i] == '0') {
            return 0;
        }
        // 第一种可能性，单独转换 i
        int ways = process(str, i + 1);
        // 第二种可能性，转换 i 和 i+1 合起来的数字
        if (i + 1 < str.length && (str[i] - '0') * 10 + str[i + 1] - '0' < 27) {
            ways += process(str, i + 2);
        }
        return ways;
    }

    /**
     * 从右往左的动态规划
     * 就是上面方法的动态规划版本
     * dp[i] 表示：str[i...] 有多少种转化方式
     */
    static int dp1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (str[i] != '0') {
                int ways = dp[i + 1];
                if (i + 1 < str.length && (str[i] - '0') * 10 + str[i + 1] - '0' < 27) {
                    ways += dp[i + 2];
                }
                dp[i] = ways;
            }
        }
        return dp[0];
    }

    /**
     * 从左往右的动态规划
     * dp[i] 表示：str[0...i] 有多少种转化方式
     */
    static int dp2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        if (str[0] == '0') {
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            if (str[i] == '0') {
                // 如果此时 str[i]=='0'，那么他是一定要拉前一个字符（i-1的字符）一起拼的，
                // 那么就要求前一个字符，不能也是‘0’，否则拼不了。
                // 前一个字符不是‘0’就够了嘛？不够，还得要求拼完了要么是 10，要么是20，如果更大的话，拼不了。
                // 这就够了嘛？还不够，你们拼完了，还得要求 str[0...i-2] 真的可以被分解！
                // 如果 str[0...i-2] 都不存在分解方案，那i和i-1拼成了也不行，因为之前的搞定不了。
                if (str[i - 1] == '0' || str[i - 1] > '2' || (i - 2 >= 0 && dp[i - 2] == 0)) {
                    return 0;
                } else {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] : 1;
                }
            } else {
                dp[i] = dp[i - 1];
                if (str[i - 1] != '0' && (str[i - 1] - '0') * 10 + str[i] - '0' <= 26) {
                    dp[i] += i - 2 >= 0 ? dp[i - 2] : 1;
                }
            }
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        int N = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            String s = StringComparator.generateRandomDigitString(len);
            int ans0 = number(s);
            int ans1 = dp1(s);
            int ans2 = dp2(s);
            if (ans0 != ans1 || ans0 != ans2) {
                System.out.println(s);
                System.out.println(ans0);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("Finish!");
    }
}
