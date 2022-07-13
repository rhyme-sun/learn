package learn.algorithm.coding.skill.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定两个字符串 `s` 和 `p`，找到 `s` 中所有 `p` 的异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * 异位词指由相同字母重排列形成的字符串（包括相同的字符串）。
 *
 * https://leetcode.cn/problems/find-all-anagrams-in-a-string
 */
public class Code17_FindAnagrams {

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        if (s.length() < p.length()) {
            return ans;
        }
        int[] restCount = new int[26];

        int m = p.length();
        for (int i = 0; i < m; i++) {
            restCount[p.charAt(i) - 'a']++;
        }
        int left = 0, right = 0;
        int valid = 0;
        while (right < s.length()) {
            char in = s.charAt(right);
            if (--restCount[in - 'a'] >= 0) {
                valid++;
            }
            right++;
            while (right - left == m) {
                if (valid == m) {
                    ans.add(left);
                }
                char out = s.charAt(left);
                if (++restCount[out - 'a'] > 0) {
                    valid--;
                }
                left++;
            }
        }
        return ans;
    }
}
