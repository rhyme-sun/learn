package learn.algorithm.basics.algorithm.resursion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字符串全组合，即打印字符串全部子序列
 * https://leetcode.cn/problems/subsets/
 */
public class Code04_PrintAllCombinations {

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
            ans.add(pre);
            return;
        }
        // 选择 index 位置字符组成子序列
        process(chars, index + 1, pre + chars[index], ans);
        // 不选择 index 位置字符组成子序列
        process(chars, index + 1, pre, ans);
    }

    // 迭代，在 0~i-1 形成子序列的集合基础上，添加 i 位置字符新增包含 i 位置的行的子序列集合，和 i-1 子序列集合合并成为 i 位置子序列集合
    static List<String> combination2(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return ans;
        }
        ans.add("");
        char[] s = str.toCharArray();
        for (int i = 0; i < s.length; i++) {
            // 上一个位置集合中子序列末尾添加 i 位置字符形成的集合
            List<String> addI = new ArrayList<>(ans.size());
            for (String pre : ans) {
                addI.add(pre + s[i]);
            }
            ans.addAll(addI);
        }
        return ans;
    }

    static List<String> combinationNoRepeat(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return ans;
        }
        ans.add("");
        Map<Character, Integer> startAddPos = new HashMap<>();
        char[] s = str.toCharArray();
        for (int i = 0; i < s.length; i++) {
            // 上一个位置集合中子序列末尾添加 i 位置字符形成的集合
            List<String> addI = new ArrayList<>(ans.size());
            int start = startAddPos.getOrDefault(s[i], 0);
            for (int j = start; j < ans.size(); j++) {
                addI.add(ans.get(j) + s[i]);
            }
            startAddPos.put(s[i], ans.size());
            ans.addAll(addI);
        }
        return ans;
    }

    public static void main(String[] args) {
        String test = "122";
        List<String> ans1 = combination(test);
        List<String> ans2 = combination2(test);
        List<String> ans3 = combinationNoRepeat(test);
        System.out.println(ans1);
        System.out.println(ans2);
        System.out.println(ans3);
    }
}
