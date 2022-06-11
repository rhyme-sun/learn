package learn.algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串全组合，即打印字符串全部子序列
 */
public class PrintAllCombinations {

    static List<String> combination(String str) {
        List<String> ans = new ArrayList<>();
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
    private static void process(char[] chars, int index, String pre, List<String> ans) {
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
        String test = "abc";
        List<String> ans = combination(test);
        for (String str : ans) {
            System.out.println(str);
        }
    }
}
