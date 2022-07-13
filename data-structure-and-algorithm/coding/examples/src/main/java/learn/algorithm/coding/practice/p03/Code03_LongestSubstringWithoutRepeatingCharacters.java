package learn.algorithm.coding.practice.p03;

/**
 * 题目描述如下：
 * 求一个字符串中，最长无重复字符子串长度。
 * leetcode: https://leetcode.cn/problems/longest-substring-without-repeating-characters/
 */
public class Code03_LongestSubstringWithoutRepeatingCharacters {

    static int lengthOfLongestSubstring2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        // 记录字符上一个出现的位置
        int[] preLocation = new int[256];
        for (int i = 0; i < preLocation.length; i++) {
            preLocation[i] = -1;
        }
        preLocation[chars[0]] = 0;
        // 讨论以 i 位置结尾最长不重复子串的长度
        int ans = 1;
        // 前一个位置的解（最长不重复子串的长度）
        int pre = 1;
        for (int i = 1; i < chars.length; i++) {
            pre = Math.min(pre + 1, i - preLocation[chars[i]]);
            ans = Math.max(pre, ans);
            preLocation[chars[i]] = i;
        }
        return ans;
    }
}
