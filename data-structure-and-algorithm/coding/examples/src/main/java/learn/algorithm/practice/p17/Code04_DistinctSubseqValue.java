package learn.algorithm.practice.p17;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串 s，计算 s 的**不同非空子序列**个数。因为结果可能很大，所以返回答案需要对 `10^9 + 7` 取余 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/distinct-subsequences-ii
 */
public class Code04_DistinctSubseqValue {

    public static int zuo(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int m = 1000000007;
        char[] str = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        int all = 1; // 一个字符也没遍历的时候，有空集
        for (char x : str) {
            int newAdd = all;
//			int curAll = all + newAdd - (map.containsKey(x) ? map.get(x) : 0);
            int curAll = all;
            curAll = (curAll + newAdd) % m;
            curAll = (curAll - (map.containsKey(x) ? map.get(x) : 0) + m) % m;
            all = curAll;
            map.put(x, newAdd);
        }
        return all - 1;
    }

    static int distinctSubseqII(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int ans = 1;
        int mod = 1000000007;
        Map<Character, Integer> newAdd = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            int preNewAdd = newAdd.getOrDefault(s.charAt(i), 0);
            newAdd.put(s.charAt(i), ans);
            ans = ((ans + ans) % mod - preNewAdd % mod + mod) % mod;
        }
        return ans - 1;
    }

    public static void main(String[] args) {
        String s = "bcafgdsagdsagadgaccsdfafdasfsdfagasddddddddgasca";
        System.out.println(distinctSubseqII(s));
        System.out.println(zuo(s));
    }

}
