package learn.algorithm.coding.practice.p09;

import java.util.HashMap;

/**
 * 题目描述如下：
 * 定义何为 step sum？比如 680，680 + 68 + 6 = 754，680 的步骤和为 754。给定一个正数 num，判断它是不是某个数的步骤和？
 */
public class Code07_IsStepSum {

    static boolean isStepSum(int num) {
        if (num < 0) {
            return false;
        }
        int l = 0;
        int r = num;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            int stepSum = stepSum(mid);
            if (stepSum == num) {
                return true;
            } else if (stepSum < num) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return false;
    }

    private static int stepSum(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num;
            num /= 10;
        }
        return sum;
    }

    // for test
    private static HashMap<Integer, Integer> generateStepSumNumberMap(int numMax) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <= numMax; i++) {
            map.put(stepSum(i), i);
        }
        return map;
    }

    // for test
    public static void main(String[] args) {
        int max = 1000000;
        int maxStepSum = stepSum(max);
        HashMap<Integer, Integer> ans = generateStepSumNumberMap(max);
        for (int i = 0; i <= maxStepSum; i++) {
            if (isStepSum(i) ^ ans.containsKey(i)) {
                System.out.println("Oops!");
                System.out.println(i);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
