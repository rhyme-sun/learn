package learn.algorithm.coding.practice.p12;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * <p>
 * 链接：https://leetcode.cn/problems/longest-consecutive-sequence
 */
public class Code03_LongestConsecutive {

    static int longestConsecutive(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int len = 0;
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
                int preLen = map.getOrDefault(num - 1, 0);
                int posLen = map.getOrDefault(num + 1, 0);
                int all = preLen + posLen + 1;
                map.put(num - preLen, all);
                map.put(num + posLen, all);
                len = Math.max(len, all);
            }
        }
        return len;
    }

    // 不是最优解，使用了两张表，更好理解
    static int longestConsecutive2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 连续区间头表
        Map<Integer, Integer> headMap = new HashMap<>();
        // 连续区间尾表
        Map<Integer, Integer> tailMap = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        for (int num : nums) {
            if (!visited.contains(num)) {
                headMap.put(num, 1);
                tailMap.put(num, 1);
                if (headMap.containsKey(num + 1)) {
                    int len = headMap.remove(num + 1);
                    headMap.put(num, len + 1);
                    tailMap.put(num + len, len + 1);
                    tailMap.remove(num);
                }
                if (tailMap.containsKey(num - 1)) {
                    // num 之前的最长序列
                    int preLen = tailMap.remove(num - 1);
                    // num 之后的最长序列
                    int posLen = headMap.remove(num) - 1;
                    // 区间合并
                    headMap.put(num - preLen, preLen + posLen + 1);
                    tailMap.put(num + posLen, preLen + posLen + 1);
                }
                visited.add(num);
            }
        }
        int max = 1;
        for (Integer value : headMap.values()) {
            max = Math.max(max, value);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = {0,3,7,2,5,8,4,6,0,1};
        int ans = longestConsecutive(nums);
        System.out.println(ans);
    }
}
