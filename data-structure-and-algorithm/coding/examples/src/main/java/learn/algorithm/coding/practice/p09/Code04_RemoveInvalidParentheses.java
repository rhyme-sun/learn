package learn.algorithm.coding.practice.p09;

import java.util.ArrayList;
import java.util.List;


/**
 * 给你一个由若干括号和字母组成的字符串 `s` ，删除最小数量的无效括号，使得输入的字符串有效。返回所有可能的结果。答案可以按 **任意顺序** 返回。
 * <p>
 * leetcode: https://leetcode.cn/problems/remove-invalid-parentheses/
 */
public class Code04_RemoveInvalidParentheses {


    static List<String> removeInvalidParentheses1(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        // 计算出至少需要删除左右括号的数量
        int lremove = 0;
        int rremove = 0;

        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (cur == '(') {
                lremove++;
            } else if (cur == ')') {
                if (lremove == 0) {
                    rremove++;
                } else {
                    lremove--;
                }
            }
        }
        List<String> ans = new ArrayList<>();
        process(s, 0, lremove, rremove, ans);
        return ans;
    }

    private static void process(String str, int start, int lremove, int rremove, List<String> ans) {
        if ((lremove == 0 && rremove == 0)) {
            if (isValid(str)) {
                ans.add(str);
            }
            return;
        }
        // 从 start 位置开始删除字符
        for (int i = start; i < str.length(); i++) {
            // 当有多个连续一样的字符时，仅删除第一个
            if (i != start && str.charAt(i) == str.charAt(i - 1)) {
                continue;
            }
            // 如果剩余的字符不够删了，直接跳过
            if (lremove + rremove > str.length() - i) {
                return;
            }
            // 选择删除当前位置的字符
            // 如果 start 前的括号序列右括号比左括号多，则 start 后面不论怎么删除，得到的字符序列都不可能合法
            if (rightMore(str.substring(0, i))) {
                return;
            }
            char cur = str.charAt(i);
            if (lremove > 0 && cur == '(') {
                process(str.substring(0, i) + str.substring(i + 1), i, lremove - 1, rremove, ans);
            }
            if (rremove > 0 && cur == ')') {
                process(str.substring(0, i) + str.substring(i + 1), i, lremove, rremove - 1, ans);
            }
        }
    }

    /**
     * 判断当前括号序列是否合法
     * 使用一个变量记录左右括号的差值，遍历序列，当出现左括号时 count++，出现右括号时 count--；
     * 如果中途 count<0，表明右括号比左括号多，说明括号序列非法；
     * 如果遍历完毕，count > 0，表明左括号多于右括号，则括号序列也非法；
     * 如果遍历完毕，count == 0，表明左括号等于右括号，则括号序列合法；
     */
    private static boolean isValid(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                count++;
            } else if (str.charAt(i) == ')') {
                count--;
                if (count < 0) {
                    return false;
                }
            }
        }
        return count == 0;
    }

    /**
     * 判断当前字符，是不是右边括号比左边多
     */
    private static boolean rightMore(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                count++;
            } else if (str.charAt(i) == ')') {
                count--;
                if (count < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 来自 leetcode 投票第一的答案，实现非常好，我们来赏析一下
     */
    static List<String> removeInvalidParentheses(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        List<String> ans = new ArrayList<>();
        remove(s, ans, 0, 0, new char[]{'(', ')'});
        return ans;
    }

    /**
     * 从 checkIndex 位置开始检查是否存在非法的括号，如果有从 deleteIndex 位置开始考察删除那些多余的括号。
     * 如果没有能够删除的括号，将答案收集到 ans 中。
     *
     * @param s           括号字符串
     * @param ans         收集答案
     * @param checkIndex  括号非法检查的起始位置
     * @param deleteIndex 括号删除考察的起始位置
     * @param par         括号对，par[1] 表示当前要删除的括号为左括号还是右括号，par[0] 和 par[1] 相反
     */
    private static void remove(String s, List<String> ans, int checkIndex, int deleteIndex, char[] par) {
        // count 遇到 char[0] 时 ++，遇到 char[1] 时 --，当 count 小于 0 的时候则说明遇到了多余的 char[1]
        for (int count = 0, i = checkIndex; i < s.length(); i++) {
            if (s.charAt(i) == par[0]) {
                count++;
            }
            if (s.charAt(i) == par[1]) {
                count--;
            }
            if (count < 0) {
                // 从 deleteIndex 到检查到非法的位置（i），删除多余的 par[1]
                // j == deleteIndex || s.charAt(j - 1) != par[1]) 属于剪枝优化，当有多个连续的右括号可以删除时，删除其中任何一个
                // 得到的字符串序列相同，所以这里删除第一个即可，比如 "())" 删除任何一个右括号得到的括号序列都为 "()"
                // j == deleteIndex 可以避免样本字符串第一个字符为 par[1] 时数组越界问题
                for (int j = deleteIndex; j <= i; j++) {
                    if (s.charAt(j) == par[1] && (j == deleteIndex || s.charAt(j - 1) != par[1])) {
                        remove(s.substring(0, j) + s.substring(j + 1), ans, i, j, par);
                    }
                }
                // 删除完后，继续向后检查不合法位置
                return;
            }
        }
        // 多余的右括号删除完毕，现在翻转字符串，删除多余的左括号
        String reversed = new StringBuilder(s).reverse().toString();
        // 这个 if 判断很重要，没有这个判断，递归会无法结束
        if (par[0] == '(') {
            remove(reversed, ans, 0, 0, new char[]{')', '('});
        } else {
            ans.add(reversed);
        }
    }

    public static void main(String[] args) {
        String str = ")(";
        List<String> ans1 = removeInvalidParentheses1(str);
        List<String> ans2 = removeInvalidParentheses(str);
        System.out.println(ans1);
        System.out.println(ans2);
    }
}
