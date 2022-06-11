package learn.algorithm.practice.p12;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定长度为 m 的字符串 aim，以及一个长度为 n 的字符串 str。
 * 问能否在 str 中找到一个长度为 m 的连续子串，使得这个子串刚好由 aim 的 m 个字符组成，顺序无所谓。
 * 返回任意满足条件的一个子串的起始位置，未找到返回 -1。
 * <p>
 * https://leetcode.cn/problems/permutation-in-string/
 */
public class Code01_ContainAllCharExactly {

    static boolean checkInclusion(String aim, String str) {
        if (aim == null || aim.length() == 0) {
            return true;
        }
        if (str == null || str.length() == 0) {
            return false;
        }
        // aim 还剩下多少个字符没有被使用
        int m = aim.length();
        Map<Character, Integer> countMap = countMap(aim);
        int l = 0, r = 0;
        while (r < str.length()) {
            // r 进入窗口
            char in = str.charAt(r);
            if (countMap.containsKey(in)) {
                int count = countMap.merge(in, -1, Integer::sum);
                if (count >= 0) {
                    m--;
                }
            }
            if (r > aim.length() - 1) {
                // l 从窗口出去
                char out = str.charAt(l++);
                if (countMap.containsKey(out)) {
                    int count = countMap.merge(out, 1, Integer::sum);
                    if (count > 0) {
                        m++;
                    }
                }
            }
            r++;
            if (m == 0) {
                return true;
            }
        }
        return false;
    }

    private static Map<Character, Integer> countMap(String str) {
        Map<Character, Integer> countMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            countMap.merge(str.charAt(i), 1, Integer::sum);
        }
        return countMap;
    }

    public static void main(String[] args) {
        String s1 = "adc", s2 = "ddcda";
        System.out.println(checkInclusion(s1, s2));
    }
}
