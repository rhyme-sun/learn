package learn.algorithm.practice.p15;

/**
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成两笔交易。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 链接：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii
 */
public class Code03_BestTimeToBuyAndSellStockIII {

    static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int ans = 0;
        // i 时刻前的一次交易的最大收益
        int doneOnceMax = 0;
        // i 时刻前的一次最大收益减去某一时刻的收益的最大值，减去某一时刻表示又买入了一股
        int doneOnceMinusBuyMax = doneOnceMax - prices[0];
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            ans = Math.max(ans, doneOnceMinusBuyMax + prices[i]);
            doneOnceMax = Math.max(doneOnceMax, prices[i] - min);
            doneOnceMinusBuyMax = Math.max(doneOnceMinusBuyMax, doneOnceMax - prices[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {7, 5, 8};
        int ans = maxProfit(arr);
        System.out.println(ans);
    }
}
