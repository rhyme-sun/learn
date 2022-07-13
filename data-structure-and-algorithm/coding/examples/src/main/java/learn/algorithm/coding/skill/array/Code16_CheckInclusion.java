package learn.algorithm.coding.skill.array;

/**
 * 给你两个字符串 `s1` 和 `s2` ，写一个函数来判断 `s2` 是否包含 `s1` 的排列。如果是，返回 true ；否则，返回 false 。
 * 换句话说，`s1` 的排列之一是 `s2` 的 子串 。
 *
 * https://leetcode.cn/problems/permutation-in-string
 */
public class Code16_CheckInclusion {

    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        int[] restCount = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            restCount[s1.charAt(i) - 'a']++;
        }
        int m = s1.length();
        int left = 0, right = 0;
        // 统计成功覆盖数量
        int valid = 0;
        while (right < s2.length()) {
            char in = s2.charAt(right);
            if (--restCount[in - 'a'] >= 0) {
                valid++;
            }
            // 扩大窗口
            right++;
            while (right - left == m) {
                if (valid == m) {
                    return true;
                }
                // 缩小窗口
                char out = s2.charAt(left);
                if (++restCount[out - 'a'] > 0) {
                    valid--;
                }
                left++;
            }
        }
        return false;
    }
}
