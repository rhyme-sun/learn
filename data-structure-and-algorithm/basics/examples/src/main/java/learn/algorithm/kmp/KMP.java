package learn.algorithm.kmp;

import learn.algorithm.comparator.StringComparator;

/**
 * 求一个字符串 str 是否包含另外一个字符串 match
 */
public class KMP {

    /**
     * 暴力方法
     */
    static int indexOf(String str, String match) {
        if (str == null || str.length() == 0 || match == null || match.length() == 0) {
            return -1;
        }
        int strLen = str.length();
        int matchLen = match.length();
        if (strLen < matchLen) {
            return -1;
        }
        int matchedCount = 0;
        char[] strChars = str.toCharArray();
        char[] matchChars = match.toCharArray();
        for (int i = 0; i <= strLen - matchLen; i++) {
            int index = i;
            for (int j = 0; j < matchChars.length; j++, index++) {
                if (strChars[index] != matchChars[j]) {
                    break;
                }
                matchedCount++;
            }
            if (matchedCount == match.length()) {
                return i;
            } else {
                matchedCount = 0;
            }
        }
        return -1;
    }

    /**
     * 使用 KMP 算法
     */
    static int getIndexOf(String str, String match) {
        if (str == null || match == null || match.length() < 1 || str.length() < match.length()) {
            return -1;
        }
        char[] strChars = str.toCharArray();
        char[] matchChars = match.toCharArray();
        // O(M) m <= n
        int[] next = getNextArray(matchChars);
        int x = 0;
        int y = 0;
        // O(N)
        while (x < strChars.length && y < matchChars.length) {
            if (strChars[x] == matchChars[y]) {
                x++;
                y++;
            } else if (next[y] == -1) { // y == 0
                x++;
            } else {
                y = next[y];
            }
        }
        return y == matchChars.length ? x - y : -1;
    }

    /**
     * 获取 next 数组
     */
    private static int[] getNextArray(char[] str) {
        if (str.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str.length];
        next[0] = -1;
        next[1] = 0;
        // 目前在哪个位置上求 next 数组的值
        int i = 2;
        // 当前是哪个位置的值在和 i-1 位置的字符比较
        int cn = 0;
        while (i < next.length) {
            if (str[i - 1] == str[cn]) { // 配成功的时候
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        int strLen = 20;
        int matchLen = 5;
        int testTimes = 100000;

        for (int i = 0; i < testTimes; i++) {
            final String str = StringComparator.generateRandomString(strLen);
            final String match = StringComparator.generateRandomString(matchLen);
            int ans1 = indexOf(str, match);
            int ans2 = str.indexOf(match);
            int ans3 = getIndexOf(str, match);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                System.out.println(str);
                System.out.println(match);
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
        System.out.println("Finish!");
    }
}
