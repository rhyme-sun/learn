package learn.algorithm.manacher;

/**
 * 题目描述如下：
 * 任意一个字符串，只能在其后面添加字符，问至少添加几个字符使得整个字符串为回文字符串，返回需要添加的字符串。
 */
public class AddShortestEnd {

    static String shortestEnd(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] str = manacherString(s);
        int[] pArr = new int[str.length];
        int C = -1;
        int R = -1;
        int maxContainsEnd = -1;
        for (int i = 0; i != str.length; i++) {
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i + 1) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]])
                    pArr[i]++;
                else {
                    break;
                }
            }
            if (i + pArr[i] - 1 > R) {
                R = i + pArr[i] - 1;
                C = i;
            }
            if (R == str.length - 1) {
                maxContainsEnd = pArr[i];
                break;
            }
        }
        // 回文直径
        int max = maxContainsEnd - 1;
        char[] res = new char[s.length() - max];
        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = str[i * 2 + 1];
        }
        return String.valueOf(res);
    }

    private static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
    }
}
