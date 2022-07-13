package learn.algorithm.coding.practice.p15;

/**
 * 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
 * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
 * 返回获得利润的最大值。
 * <p>
 * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
 * 链接：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee
 */
public class Code06_BestTimeToBuyAndSellStockWithTransactionFee {

    // buy[i] 表示在 0~i 位置最后一次操作为买入的最优收益
    // sell[i] 表示在 0~i 位置最后一次操作为卖出的最优收益
    static int maxProfit(int[] prices, int fee) {
        if (prices.length < 2) {
            return 0;
        }
        int n = prices.length;
        int[] buy = new int[n];
        int[] sell = new int[n];
        // 0 位置只能买入，最优收入为 -prices[0]-fee
        buy[0] = -prices[0] - fee;
        // 0 位置只能卖出，最优收入为 0
        sell[0] = 0;
        for (int i = 1; i < n; i++) {
            buy[i] = Math.max(buy[i - 1], sell[i - 1] - prices[i] - fee);
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }
        return sell[n - 1];
    }

    // 空间压缩
    static int maxProfit2(int[] arr, int fee) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        // 0..0   0 -[0] - fee
        int bestbuy = -arr[0] - fee;
        // 0..0  卖  0
        int bestsell = 0;
        for (int i = 1; i < N; i++) {
            // 来到i位置了！
            // 如果在i必须买  收入 - 批发价 - fee
            int curbuy = bestsell - arr[i] - fee;
            // 如果在i必须卖  整体最优（收入 - 良好批发价 - fee）
            int cursell = bestbuy + arr[i];
            bestbuy = Math.max(bestbuy, curbuy);
            bestsell = Math.max(bestsell, cursell);
        }
        return bestsell;
    }
}
