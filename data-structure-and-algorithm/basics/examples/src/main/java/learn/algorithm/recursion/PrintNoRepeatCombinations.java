package learn.algorithm.recursion;

import java.util.HashSet;
import java.util.Set;

/**
 * 字符串全组合，即打印字符串全部子序列，且去除重复子序列
 */
public class PrintNoRepeatCombinations {

    static Set<String> combination(String str) {
        Set<String> ans = new HashSet<>();
        if (str == null || str.length() == 0) {
            return ans;
        }
        process(str.toCharArray(), 0, "", ans);
        return ans;
    }

    /**
     * 考虑当前来到 index 位置，选择要不要 index 位置的字符组成子序列
     *
     * @param pre index 位置字符组成的子序列
     */
    private static void process(char[] chars, int index, String pre, Set<String> ans) {
        if (index == chars.length) {
            if (pre.length() > 0) {
                ans.add(pre);
            }
            return;
        }
        // 选择 index 位置字符组成子序列
        process(chars, index + 1, pre + chars[index], ans);
        // 不选择 index 位置字符组成子序列
        process(chars, index + 1, pre, ans);
    }

    public static void main(String[] args) {
        String test = "acc";
        Set<String> ans = combination(test);
        for (String str : ans) {
            System.out.println(str);
        }
    }
}
