package learn.algorithm.coding.practice.p08;

/**
 * leetcode: https://leetcode.cn/problems/container-with-most-water/
 */
public class Code02_ContainerWithMostWater {

    /**
     * 暴力解
     */
    static int maxArea1(int[] h) {
        int max = 0;
        int N = h.length;
        for (int i = 0; i < N; i++) { // h[i]
            for (int j = i + 1; j < N; j++) { // h[j]
                max = Math.max(max, Math.min(h[i], h[j]) * (j - i));
            }
        }
        return max;
    }

    /**
     * 使用左右指针
     */
    static int maxArea2(int[] height) {
        int max = -1;
        int l = 0;
        int r = height.length - 1;
        while (l<r) {
            if (height[l] < height[r]) {
                max = Math.max(max, height[l] * (r-l));
                l++;
            } else {
                max = Math.max(max, height[r] * (r-l));
                r--;
            }
        }
        return max;
    }
}
