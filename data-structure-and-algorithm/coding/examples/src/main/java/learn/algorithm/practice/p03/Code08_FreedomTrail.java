package learn.algorithm.practice.p03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目描述如下：
 * 电子游戏“辐射4”中，任务 “通向自由” 要求玩家到达名为 “Freedom Trail Ring” 的金属表盘，并使用表盘拼写特定关键词才能开门。
 * <p>
 * 这个金属表盘相当于一个环形电话字符串，指针一直指向 12 点位置。每次可以逆时针或顺时针旋转字符到指针指向的位置，按下确定按钮可选择该字符。
 * 给定另一个字符串 key ，表示需要拼写的关键词。您需要算出能够拼写关键词中所有字符的最少步数，按下确认也算作一步。
 */
public class Code08_FreedomTrail {

    static int findRotateSteps(String r, String k) {
        if (r == null || r.length() == 0 || k == null || k.length() == 0) {
            return 0;
        }
        char[] ring = r.toCharArray();
        char[] key = k.toCharArray();
        Map<Character, List<Integer>> charPos = charPos(ring);
        return process(0, 0, key, charPos, ring.length);
    }

    /**
     * 假设当前需要拨 index 位置的字符（前面的字符均以拨完），指针指向的位置为 p，考虑拨完后面的字符的最少拨号次数
     *
     * @param p       指针指向表盘的位置
     * @param index   需要拨的字符位置
     * @param key     号码字符数组
     * @param charPos 表盘每个字符在表盘上的位置
     * @param n       表盘字符长度
     * @return 最少拨号次数
     */
    private static int process(int p, int index, char[] key, Map<Character, List<Integer>> charPos, int n) {
        if (index == key.length) {
            return 0;
        }
        // 下个拨号字符可以去的位置
        List<Integer> pos = charPos.get(key[index]);
        int ans = Integer.MAX_VALUE;
        for (int next : pos) {
            // p 拨向 next
            int cost = dail(p, next, n) + process(next, index + 1, key, charPos, n);
            ans = Math.min(ans, cost);
        }
        return ans;
    }

    /**
     * 记忆化搜索优化
     */
    static int findRotateSteps2(String r, String k) {
        if (r == null || r.length() == 0 || k == null || k.length() == 0) {
            return 0;
        }
        char[] ring = r.toCharArray();
        char[] key = k.toCharArray();
        Map<Character, List<Integer>> charPos = charPos(ring);

        int[][] dp = new int[ring.length + 1][key.length + 1];
        return process(0, 0, key, charPos, ring.length, dp);
    }

    private static int process(int p, int index, char[] key, Map<Character, List<Integer>> charPos, int n, int[][] dp) {
        if (dp[p][index] != 0) {
            return dp[p][index];
        }
        if (index == key.length) {
            return 0;
        }
        // 下个拨号字符可以去的位置
        List<Integer> pos = charPos.get(key[index]);
        int ans = Integer.MAX_VALUE;
        for (int next : pos) {
            // p 拨向 next
            int cost = dail(p, next, n) + process(next, index + 1, key, charPos, n, dp);
            ans = Math.min(ans, cost);
        }
        dp[p][index] = ans;
        return ans;
    }

    private static Map<Character, List<Integer>> charPos(char[] ring) {
        Map<Character, List<Integer>> charPos = new HashMap<>();
        for (int i = 0; i < ring.length; i++) {
            List<Integer> pos = charPos.computeIfAbsent(ring[i], ArrayList::new);
            pos.add(i);
        }
        return charPos;
    }

    /**
     * 模拟拨号最佳拨号行为，返回最少拨号次数（按下确认也算作一步）
     * 0 1 2 3 4 5
     * s   e
     *
     * @param start 拨号起始位置
     * @param end   拨号结束位置
     * @param size  电话盘总大小
     */
    private static int dail(int start, int end, int size) {
        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }
        // 没有越过数组结尾
        int cost1 = end - start;
        // 越过数组结尾
        int cost2 = size - 1 - end + start + 1;
        return Math.min(cost1, cost2) + 1;
    }

    public static void main(String[] args) {
        String ring = "godding", key = "gd";
        int ans = findRotateSteps2(ring, key);
        System.out.println(ans);
    }
}
