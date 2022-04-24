package learn.algorithm.structure.monotonousstack;

import java.util.Stack;

/**
 * 题目描述如下：
 * 给定一个二维数组 matrix，其中的值不是 0 就是 1，返回全部由 1 组成的最大子矩形，内部有多少个 1。
 * leetcode: https://leetcode-cn.com/problems/maximal-rectangle/
 */
public class MaximalRectangle {

    static int maximalRectangle(char[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) {
            return 0;
        }
        int maxArea = 0;
        int[] height = new int[map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                height[j] = map[i][j] == '0' ? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxRecFromBottom(height), maxArea);
        }
        return maxArea;
    }

    /**
     * height 是直方图图数组
     */
    private static int maxRecFromBottom(int[] height) {
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                // 宽度计算：i-1-(k+1)+1= i-k-1
                int curArea = (i - k - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (height.length - k - 1) * height[j];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }

    /**
     * height 是直方图图数组，使用数组替代栈
     */
    private static int maxRecFromBottom2(int[] height) {
        int maxArea = 0;
        //Stack<Integer> stack = new Stack<>();
        // 指向栈顶
        int si = -1;
        int[] stack = new int[height.length];
        for (int i = 0; i < height.length; i++) {
            while (si != -1 && height[i] <= height[stack[si]]) {
                //int j = stack.pop();
                int j = stack[si--];
                int k = si == -1 ? -1 : stack[si];
                // 宽度计算：i-1-(k+1)+1= i-k-1
                int curArea = (i - k - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack[++si] = i;
            // stack.push(i);
        }
        while (si != -1) {
            int j = stack[si--];
            int k = si == -1 ? -1 : stack[si];
            int curArea = (height.length - k - 1) * height[j];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }
}
