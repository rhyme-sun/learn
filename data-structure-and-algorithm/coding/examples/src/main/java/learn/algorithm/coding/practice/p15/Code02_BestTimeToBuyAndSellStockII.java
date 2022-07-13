package learn.algorithm.coding.practice.p15;

/**
 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
 * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候最多只能持有 一股股票。你也可以先购买，然后在同一天出售。
 * 返回你能获得的最大利润。
 * <p>
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii
 */
public class Code02_BestTimeToBuyAndSellStockII {

    static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            ans += Math.max(prices[i] - prices[i - 1], 0);
        }
        return ans;
    }
}
