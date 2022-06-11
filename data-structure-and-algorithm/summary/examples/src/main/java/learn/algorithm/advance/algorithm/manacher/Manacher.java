package learn.algorithm.advance.algorithm.manacher;

import learn.algorithm.comparator.StringComparator;

/**
 * 求最长回文子字符串的长度
 */
public class Manacher {

    static int maxPalindromeSubString(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int max = 0;
        char[] chars = manacherString(str);
        int n = chars.length;
        for (int i = 0; i < chars.length - 1; i++) {
            int l = i - 1;
            int r = i + 1;
            while (l > -1 && r < n && chars[l] == chars[r]) {
                l--;
                r++;
            }
            // (r-1)-(l+1)+1 = r-l-1
            max = Math.max(max, r - l - 1);
        }
        return max / 2;
    }

    static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        // 回文半径的大小
        int[] pArr = new int[str.length];
        // 最右回文中心
        int C = -1;
        // 最右回文边界
        int R = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) { // 0 1 2
            // R > i，R 罩住了 i
            // 2 * C - i 即 i' 求 i' 的回文半径和 R-i+1 中较小的那个，参与下面的回文判断
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i + 1) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]])
                    pArr[i]++;
                else {
                    break;
                }
            }
            // 更新 R 和 C
            if (i + pArr[i] - 1 > R) {
                R = i + pArr[i] - 1;
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        // max 最大的 mancher 字符串的回文半径，减去 1 就是原始字符串的最大回文直径
        return max - 1;
    }

    /**
     * abba -> #a#b#b#a
     * aba -> #a#b#a#
     */
    private static char[] manacherString(String str) {
        char[] chars = str.toCharArray();
        StringBuilder manacherString = new StringBuilder();
        manacherString.append("#");
        for (char aChar : chars) {
            manacherString.append(aChar).append("#");
        }
        return manacherString.toString().toCharArray();
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        for (int i = 0; i < testTimes; i++) {
            String str = StringComparator.generateRandomString(possibilities, strSize);
            int ans1 = maxPalindromeSubString(str);
            int ans2 = manacher(str);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(str);
            }
        }
        System.out.println("Finish!");
    }
}
