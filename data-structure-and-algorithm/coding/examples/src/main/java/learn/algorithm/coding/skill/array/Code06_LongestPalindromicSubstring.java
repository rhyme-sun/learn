package learn.algorithm.coding.skill.array;

/**
 * 下面看一个和回文串相关的问题，求字符串的最长回文子串。
 * <p>
 * https://leetcode.cn/problems/longest-palindromic-substring/
 */
public class Code06_LongestPalindromicSubstring {

    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        String ans = "";
        String warpString = wrapString(s);
        int n = warpString.length();
        for (int i = 1; i < n; i++) {
            int l = i, r = i;
            while (l >= 0 && r < n && warpString.charAt(l) == warpString.charAt(r)) {
                l--;
                r++;
            }
            if (ans.length() < r - l - 1) {
                ans = warpString.substring(l + 1, r);
            }
        }
        return unwrapString(ans);
    }

    private static String wrapString(String str) {
        StringBuilder s = new StringBuilder();
        s.append("#");
        for (int i = 0; i < str.length(); i++) {
            s.append(str.charAt(i)).append("#");
        }
        return s.toString();
    }

    private static String unwrapString(String str) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '#') {
                s.append(str.charAt(i));
            }
        }
        return s.toString();
    }

    // 方法二：
    public static String longestPalindrome2(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        int n = s.length();
        int l = 0, r = 0;
        for (int i = 0; i < n; i++) {
            int[] range1 = palindrome(s, i, i);
            if (r - l < range1[1] - range1[0]) {
                l = range1[0];
                r = range1[1];
            }
            int[] range2 = palindrome(s, i, i + 1);
            if (r - l < range2[1] - range2[0]) {
                l = range2[0];
                r = range2[1];
            }
        }
        return s.substring(l + 1, r);
    }

    private static int[] palindrome(String s, int l, int r) {
        // 防止索引越界
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        // 返回以 s[l] 和 s[r] 为中心的最长回文串
        return new int[]{l, r};
    }


    public static void main(String[] args) {
        String str = "cbbd";
        System.out.println(longestPalindrome(str));
        System.out.println(longestPalindrome2(str));
    }
}
