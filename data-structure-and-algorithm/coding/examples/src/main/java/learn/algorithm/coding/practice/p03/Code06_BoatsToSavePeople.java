package learn.algorithm.coding.practice.p03;

import java.util.Arrays;

/**
 * 给定一个正数数组 arr，代表若干人的体重。再给定一个正数 limit，表示所有船共同拥有的载重量。
 * 每艘船最多坐两人，且不能超过载重，想让所有的人同时过河，并且用最好的分配方法让船尽量少，返回最少的船数。
 * <p>
 * 题目描述如下：
 * leetcode: https://leetcode.cn/problems/boats-to-save-people/
 */
public class Code06_BoatsToSavePeople {

    static int numRescueBoats(int[] people, int limit) {
        if (people == null || people.length == 0 || limit < 1) {
            return 0;
        }
        Arrays.sort(people);
        int ans = 0;
        int l = 0, r = people.length - 1;
        while (l <= r) {
            int sum = l == r ? people[l] : people[l] + people[r];
            if (sum > limit) {
                r--;
            } else {
                r--;
                l++;
            }
            ans++;
        }
        return ans;
    }
}
