package learn.algorithm.dp.status;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目描述如下：
 * 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，先使得累计整数和达到或超过 100 的玩家，即为胜者。
 * 如果我们将游戏规则改为 “玩家不能重复使用整数” 呢？
 * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
 * <p>
 * leetcode: https://leetcode-cn.com/problems/can-i-win
 */
public class CanIWin {

    static boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }

        int[] choose = new int[maxChoosableInteger];
        int sum = 0;
        for (int i = 0; i < maxChoosableInteger; i++) {
            choose[i] = i + 1;
            sum += choose[i];
        }
        if (sum < desiredTotal) {
            return false;
        }
        // choose 选择完毕了，还未达到 rest，直接返回 false
        return process(choose, desiredTotal);
    }

    /**
     * 考虑当前为先手，还剩 rest 的值可以累加，是否能赢
     * choose[i] 表示可以选择累加的值，值为 -1 表示已经被选择过
     */
    private static boolean process(int[] choose, int rest) {
        if (rest <= 0) {
            return false;
        }
        // 考虑先手选择的全部情况，有一种情况能赢就返回 true
        for (int i = 0; i < choose.length; i++) {
            int c = choose[i];
            if (c != -1) {
                choose[i] = -1;
                // 先手能赢，代表后手输了
                boolean next = process(choose, rest - c);
                choose[i] = c;
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean canIWin2(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        if ((maxChoosableInteger * (maxChoosableInteger + 1) >> 1) < desiredTotal) {
            return false;
        }
        return process2(maxChoosableInteger, 0, desiredTotal);
    }

    /**
     * 考虑当前为先手，还剩 rest 的值可以累加，是否能赢
     *
     * @param choose 可以选的最大值 1 <= choose <= 20
     * @param status 使用位来表示对应的值是否选择，比如 00001 表示选择了 1，000011 表示 1 和 2 都已经选择
     */
    private static boolean process2(int choose, int status, int rest) {
        if (rest <= 0) {
            return false;
        }
        for (int i = 1; i <= choose; i++) {
            boolean notChoose = (1 << (i - 1) & status) == 0;
            if (notChoose) {
                boolean next = process2(choose, (1 << (i - 1) | status), rest - i);
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean canIWin3(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        if ((maxChoosableInteger * (maxChoosableInteger + 1) >> 1) < desiredTotal) {
            return false;
        }
        return process3(maxChoosableInteger, 0, desiredTotal, new HashMap<>());
    }

    /**
     * 考虑当前为先手，还剩 rest 的值可以累加，是否能赢，使用 map 去缓存
     *
     * @param choose 可以选的最大值 1 <= choose <= 20
     * @param status 使用位来表示对应的值是否选择，比如 00001 表示选择了 1，000011 表示 1 和 2 都已经选择
     * @param dp     key 为 status，value 为 status 下先手能赢的结果
     */
    private static boolean process3(int choose, int status, int rest, Map<Integer, Boolean> dp) {
        if (dp.get(status) != null) {
            return dp.get(status);
        }
        boolean ans = false;
        if (rest > 0) {
            for (int i = 1; i <= choose; i++) {
                boolean notChoose = (1 << (i - 1) & status) == 0;
                if (notChoose) {
                    boolean next = process3(choose, (1 << (i - 1) | status), rest - i, dp);
                    if (!next) {
                        ans = true;
                    }
                }
            }
        }
        dp.put(status, ans);
        return ans;
    }

    static boolean canIWin4(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        if ((maxChoosableInteger * (maxChoosableInteger + 1) >> 1) < desiredTotal) {
            return false;
        }
        int[] dp = new int[1 << maxChoosableInteger];
        return process4(maxChoosableInteger, 0, desiredTotal, dp);
    }

    /**
     * 考虑当前为先手，还剩 rest 的值可以累加，是否能赢，使用数组去缓存
     *
     * @param choose 可以选的最大值 1 <= choose <= 20
     * @param status 使用位来表示对应的值是否选择，比如 00001 表示选择了 1，000011 表示 1 和 2 都已经选择
     * @param dp     缓存表，dp[i] 表示 status = i 时先手能赢的结果
     *               dp[i] == 0，表示 status 还未计算
     *               dp[i] == -1，表示 status 下，先手输
     *               dp[i] == 1，表示 status 下，先手赢
     */
    private static boolean process4(int choose, int status, int rest, int[] dp) {
        if (dp[status] != 0) {
            return dp[status] == 1 ? true : false;
        }
        boolean ans = false;
        if (rest > 0) {
            for (int i = 1; i <= choose; i++) {
                boolean notChoose = (1 << (i - 1) & status) == 0;
                if (notChoose) {
                    boolean next = process4(choose, (1 << (i - 1) | status), rest - i, dp);
                    if (!next) {
                        ans = true;
                    }
                }
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }

    public static void main(String[] args) {
        int maxChoosableInteger = 10;
        int desiredTotal = 1;
        System.out.println(canIWin(maxChoosableInteger, desiredTotal));
        System.out.println(canIWin2(maxChoosableInteger, desiredTotal));
        System.out.println(canIWin3(maxChoosableInteger, desiredTotal));
        System.out.println(canIWin4(maxChoosableInteger, desiredTotal));
    }
}
