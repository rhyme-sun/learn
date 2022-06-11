package learn.algorithm.practice.p05;

import java.util.Stack;

/**
 * 题目描述如下：
 * 已知一棵搜索二叉树上没有重复值的节点，现在有一个数组 arr，是这棵搜索二叉树先序遍历的结果。请根据 arr 生成整棵树并返回头节点。
 * leetcode: https://leetcode.cn/problems/construct-binary-search-tree-from-preorder-traversal/
 */
public class Code01_ConstructBinarySearchTreeFromPreorderTraversal {

    static TreeNode bstFromPreorder(int[] pre) {
        if (pre == null || pre.length == 0) {
            return null;
        }
        return process(pre, 0, pre.length - 1);
    }

    /**
     * 考虑在 [l,r] 范围内构建搜索二叉树
     */
    private static TreeNode process(int[] pre, int l, int r) {
        if (l > r) {
            return null;
        }
        TreeNode parent = new TreeNode(pre[l]);
        // l 右侧最近最大的数的位置
        int nearBig = r + 1;
        for (int i = l + 1; i <= r; i++) {
            if (pre[i] > pre[l]) {
                nearBig = i;
                break;
            }
        }
        parent.left = process(pre, l + 1, nearBig - 1);
        parent.right = process(pre, nearBig, r);
        return parent;
    }

    static TreeNode bstFromPreorder1(int[] pre) {
        if (pre == null || pre.length == 0) {
            return null;
        }
        int[] rightNearBig = rightNearBig(pre);
        return process1(pre, 0, pre.length - 1, rightNearBig);
    }

    private static TreeNode process1(int[] pre, int l, int r, int[] rightNearBig) {
        if (l > r) {
            return null;
        }
        TreeNode parent = new TreeNode(pre[l]);
        // l 右侧最近最大的数的位置
        int nearBig = (rightNearBig[l] == -1 || rightNearBig[l] > r) ? r + 1 : rightNearBig[l];
        parent.left = process1(pre, l + 1, nearBig - 1, rightNearBig);
        parent.right = process1(pre, nearBig, r, rightNearBig);
        return parent;
    }

    // arr 每个位置右侧最近最大值，没有最大记为 -1
    private static int[] rightNearBig(int[] arr) {
        int[] ans = new int[arr.length];
        // 栈顶到栈尾递减排列
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[i] > arr[stack.peek()]) {
                int pop = stack.pop();
                ans[pop] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int pop = stack.pop();
            ans[pop] = -1;
        }
        return ans;
    }

    // 使用数组代替单调栈
    private static int[] rightNearBig2(int[] arr) {
        int[] ans = new int[arr.length];
        // 栈顶到栈尾递减排列
        int top = -1;
        int size = 0;
        int[] stack = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            while (size != 0 && arr[i] > arr[stack[top]]) {
                int pop = stack[top--];
                size--;
                ans[pop] = i;
            }
            stack[++top] = i;
            size++;
        }
        while (size != 0) {
            int pop = stack[top--];
            size--;
            ans[pop] = -1;
        }
        return ans;
    }



    private static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        int[] pre = new int[]{8, 5, 1, 7, 10, 12};
        bstFromPreorder(pre);
        bstFromPreorder1(pre);
    }
}
