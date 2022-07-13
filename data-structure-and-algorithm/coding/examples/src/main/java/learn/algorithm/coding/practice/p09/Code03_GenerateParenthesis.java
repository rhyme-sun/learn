package learn.algorithm.coding.practice.p09;

import java.util.ArrayList;
import java.util.List;

/**
 * 数字 `n` 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且**有效的**括号组合。
 * <p>
 * leetcode: https://leetcode.cn/problems/generate-parentheses/
 */
public class Code03_GenerateParenthesis {

    static List<String> generateParenthesis(int n) {
        if (n < 1) {
            return null;
        }
        char[] seq = new char[n << 1];
        List<String> ans = new ArrayList<>();
        process(seq, 0, ans);
        return ans;
    }

    /**
     * 当前来到 index 位置，考虑在 index 及之后的位置设置括号能够得到的括号序列
     *
     * @param seq   括号序列，存放每个位置设置的括号
     * @param index 0<=index<=2n-1
     * @param ans   存放合法的括号字符串
     */
    private static void process(char[] seq, int index, List<String> ans) {
        if (index == seq.length) {
            if (isValid(seq)) {
                ans.add(new String(seq));
            }
            return;
        }
        // index 位置设置左括号
        seq[index] = '(';
        process(seq, index + 1, ans);
        // index 位置设置右括号
        seq[index] = ')';
        process(seq, index + 1, ans);
    }

    /**
     * 判断当前括号序列是否合法
     * 使用一个变量记录左右括号的差值，遍历序列，当出现左括号时 count++，出现右括号时 count--；
     * 如果中途 count<0，表明右括号比左括号多，说明括号序列非法；
     * 如果遍历完毕，count > 0，表明左括号多于右括号，则括号序列也非法；
     * 如果遍历完毕，count == 0，表明左括号等于右括号，则括号序列合法；
     */
    private static boolean isValid(char[] chars) {
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ')') {
                count--;
                if (count < 0) {
                    return false;
                }
            } else {
                count++;
            }
        }
        return count == 0;
    }

    static List<String> generateParenthesis2(int n) {
        if (n < 1) {
            return null;
        }
        List<String> ans = new ArrayList<>();
        process(new StringBuilder(), n, n, ans);
        return ans;
    }

    /**
     * 前面已经生成的括号序列为 s，还剩下 leftRest 的左括号和 rightRest 的右括号，求所以括号放置完可以生成的括号序列
     */
    private static void process(StringBuilder s, int leftRest, int rightRest, List<String> ans) {
        if (leftRest == 0 && rightRest == 0) {
            ans.add(s.toString());
            return;
        }
        // 左右括号至少有一个有剩余
        // 只有左括号有剩余时才能选择放置左括号
        if (leftRest > 0) {
            s.append("(");
            process(s, leftRest - 1, rightRest, ans);
            // 恢复现场
            s.deleteCharAt(s.length() - 1);
        }
        // 只有右括号还有剩余，且放置的左括号数量大于右括号数量时才能放右括号
        if (rightRest > 0 && rightRest > leftRest) {
            s.append(")");
            process(s, leftRest, rightRest - 1, ans);
            // 恢复现场
            s.deleteCharAt(s.length() - 1);
        }
    }

    public static void main(String[] args) {
        int n = 2;
        List<String> ans1 = generateParenthesis(n);
        List<String> ans2 = generateParenthesis2(n);

        System.out.println(ans1);
        System.out.println(ans2);
    }
}
