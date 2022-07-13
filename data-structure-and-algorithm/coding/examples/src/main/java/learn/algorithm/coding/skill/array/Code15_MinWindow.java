package learn.algorithm.coding.skill.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个字符串 `s` 、一个字符串 `t` 。返回 `s` 中涵盖 `t` 所有字符的最小子串。如果 `s` 中不存在涵盖 `t` 所有字符的子串，则返回空字符串 `""` 。
 * <p>
 * https://leetcode.cn/problems/minimum-window-substring/
 */
public class Code15_MinWindow {

    public static String minWindow(String s, String t) {
        if (s.length() < t.length() || s.length() == 0) {
            return "";
        }
        // 统计 t 中每个字符出现的次数
        // key 为 t 中字符，value 为剩余需要覆盖的数量
        Map<Character, Integer> restCountMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            restCountMap.merge(t.charAt(i), 1, Integer::sum);
        }

        int left = 0, right = 0;
        // 记录 s 已经覆盖 t 的字符数量
        int valid = 0;
        // 记录最小覆盖子串的起始位置和长度
        int start = 0, len = Integer.MAX_VALUE;
        while (right < s.length()) {
            char in = s.charAt(right);
            if (restCountMap.containsKey(in)) {
                if (restCountMap.merge(in, -1, Integer::sum) >= 0) {
                    valid++;
                }
            }
            // 扩大窗口
            right++;
            // System.out.println("left " + left + " right " + right);
            while (valid == t.length()) {
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                char out = s.charAt(left);
                if (restCountMap.containsKey(out)) {
                   if (restCountMap.merge(out, 1, Integer::sum) > 0) {
                       valid--;
                   }
                }
                // 缩小窗口
                left++;
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    public static void main(String[] args) {
        String s = "AAABBCADEBANC", t = "ABC";
        System.out.println(minWindow(s, t));
    }
}
