package learn.algorithm.coding.practice.p08;

import java.util.Stack;

/**
 * 带括号的四则运算
 * <p>
 * leetcode:  https://leetcode.cn/problems/basic-calculator-iii/
 */
public class Code01_ExpressionCompute {

    /**
     * 带括号的四则运算
     */
    static int calculate(String str) {
        return process(str.toCharArray(), 0)[0];
    }

    /**
     * 从 str[i...] 往下算，遇到字符串终止位置或者右括号，就停止
     * <p>
     * 返回两个值，长度为 2 的数组
     * ans[0] 表示这一段的计算结果
     * ans[1] 表示这一段计算到的位置
     */
    public static int[] process(char[] str, int i) {
        int cur = 0;
        Stack<String> stack = new Stack<>();
        // 从 i 出发，开始撸串
        while (i < str.length && str[i] != ')') {
            if (str[i] >= '0' && str[i] <= '9') {
                cur = cur * 10 + str[i++] - '0';
            } else if (str[i] != '(') { // 遇到的是运算符号
                addNum(stack, cur);
                stack.add(String.valueOf(str[i++]));
                cur = 0;
            } else { // 遇到左括号了
                int[] ans = process(str, i + 1);
                cur = ans[0];
                i = ans[1] + 1;
            }
        }
        addNum(stack, cur);
        return new int[]{getNum(stack), i};
    }

    /**
     * 不带括号的四则运算
     */
    static int simpleCalculate(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        Stack<String> stack = new Stack<>();
        int cur = 0;
        for (int i = 0; i < chars.length; i++) {
            char curChar = chars[i];
            if (curChar >= '0' && curChar <= '9') {
                cur = cur * 10 + (curChar - '0');
            } else {
                // 遇到符号，先将数字压栈，再将运算符号压栈
                addNum(stack, cur);
                stack.add(String.valueOf(curChar));
                cur = 0;
            }
        }
        // 将最后一个数字压栈
        stack.add(cur + "");
        // 根据栈内的元素进行运算
        return getNum(stack);
    }

    /**
     * 将数字入栈
     */
    private static void addNum(Stack<String> stack, int num) {
        if (!stack.isEmpty()) {
            String operator = stack.peek();
            if ("*".equals(operator) || "/".equals(operator)) {
                // 弹出乘除号
                stack.pop();
                // 弹出上个元素
                int preNum = Integer.valueOf(stack.pop());
                num = operator.equals("*") ? preNum * num : preNum / num;
            }
        }
        stack.add(num + "");
    }

    /**
     * 根据栈中元素，计算结果，从前往后计算（从栈底到栈顶）
     */
    private static int getNum(Stack<String> stack) {
        // 因为要从前往后计算，所以这里将栈逆序一下
        Stack<String> reverseStack = new Stack<>();
        while (!stack.isEmpty()) {
            reverseStack.add(stack.pop());
        }
        int cur = Integer.valueOf(reverseStack.pop());
        while (!reverseStack.isEmpty()) {
            String operator = reverseStack.pop();
            int nextNum = Integer.valueOf(reverseStack.pop());
            cur = operator.equals("+") ? cur + nextNum : cur - nextNum;
        }
        return cur;
    }

    public static void main(String[] args) {
        String str = "1+2*3-6/2+1";
        int ans = simpleCalculate(str);


        String str2 = "1+2*(3-6)/2+1-(-1)";
        int ans2 = calculate(str2);
        System.out.println(ans);
        System.out.println(ans2);
    }
}
