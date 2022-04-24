package learn.algorithm.structure.monotonousstack;

/**
 * 题目描述如下：
 * 给定一个二维数组 matrix，其中的值不是 0 就是 1，返回全部由 1 组成的子矩形数量。
 * leetcode 链接：https://leetcode.com/problems/count-submatrices-with-all-ones
 */
public class CountSubmatricesWithAllOnes {

    static int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }
        int nums = 0;
        int[] height = new int[mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
            }
            nums += countFromBottom(height);
        }
        return nums;

    }

    private static int countFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int nums = 0;
        int[] stack = new int[height.length];
        int si = -1;
        for (int i = 0; i < height.length; i++) {
            while (si != -1 && height[stack[si]] >= height[i]) {
                int cur = stack[si--];
                // 被相等的值的从栈中弹出时不计算，对于相等的元素，求矩阵数量时以最后一个为准
                if (height[cur] > height[i]) {
                    int left = si == -1 ? -1 : stack[si];
                    int n = i - left - 1;
                    int down = Math.max(left == -1 ? 0 : height[left], height[i]);
                    nums += (height[cur] - down) * num(n);
                }
            }
            stack[++si] = i;
        }
        while (si != -1) {
            int cur = stack[si--];
            int left = si == -1 ? -1 : stack[si];
            int n = height.length - left - 1;
            int down = left == -1 ? 0 : height[left];
            nums += (height[cur] - down) * num(n);
        }
        return nums;
    }

    private static int num(int n) {
        return ((n * (1 + n)) >> 1);
    }
}
