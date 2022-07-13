package learn.algorithm.coding.practice.p10;

import java.util.Map;

/**
 * 给你一个非负整数数组 nums ，你最初位于数组的第一个位置。数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。假设你总是可以到达数组的最后一个位置。
 * <p>
 * 链接：https://leetcode.cn/problems/jump-game-ii
 */
public class Code01_JumpGame {

    static int jump(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 步数
        int steps = 0;
        // 当前步能达到的最远距离
        int cur = 0;
        // 下一步能够到达的最远距离
        int next = 0;
        // 注意这里是小于 arr.length - 1
        for (int i = 0; i < arr.length - 1; i++) {
            // 考察下一步能够达到的最远距离
            next = Math.max(i + arr[i], next);
            // 来到了当前步数的极限位置，增加步数
            if (i == cur) {
                cur = next;
                steps++;
            }
        }
        return steps;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 1, 1, 4};
        int ans = jump(arr);
        Map map;
        System.out.println(ans);
    }
}
