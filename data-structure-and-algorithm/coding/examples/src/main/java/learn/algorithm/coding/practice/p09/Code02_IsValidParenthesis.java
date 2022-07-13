package learn.algorithm.coding.practice.p09;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 题目描述如下：
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 *
 * leetcode:
 */
public class Code02_IsValidParenthesis {

    private static Map<Character, Character> pairs;

    static {
        pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');
    }

    static boolean isValid(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int n = str.length();
        if ((n & 1) == 1) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // 为右括号
            if (pairs.containsKey(ch)) {
                // 栈为空，或者栈顶左括号和当前右括号不匹配，则为非法
                if (stack.isEmpty() || stack.peek() != pairs.get(ch)) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String str = "([]){}";
        boolean ans = isValid(str);
        System.out.println(ans);
    }
}
