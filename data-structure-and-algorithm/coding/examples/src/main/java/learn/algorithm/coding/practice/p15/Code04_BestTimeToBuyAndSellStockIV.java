package learn.algorithm.coding.practice.p15;

import java.util.PriorityQueue;

import learn.algorithm.coding.comparator.ArrayComparator;

//leetcode 188
public class Code04_BestTimeToBuyAndSellStockIV {

	static int maxProfit(int k, int[] prices) {
		if (prices == null || prices.length == 0 || k < 1) {
			return 0;
		}
		int n = prices.length;
		// dp[i][j] 0~i 不超过 k 最大收益
		int[][] dp = new int[n][k+1];
		// dp[0][j] = 0 dp[i][0] 0
		for (int j = 1; j <= k; j++) {
			// dp[i][j-1] - prices[i] 最大值的迭代比较结果
			int best = dp[0][j-1] - prices[0];
			for (int i = 1; i < n; i++) {
				best = Math.max(best, dp[i][j-1] - prices[i]);
				dp[i][j] = Math.max(best + prices[i], dp[i-1][j]);
			}
		}
		return dp[n-1][k];
	}

	// 使用堆，top k
	// 此贪心策略不行
	static int maxProfit2(int k, int[] prices) {
		if (prices == null || prices.length == 0 || k < 1) {
			return 0;
		}
		PriorityQueue<Integer> heap = new PriorityQueue(k);
		// 记录前一个波谷的位置
		int value = 0;
		for (int i = 1; i < prices.length; i++) {
			if (prices[i] >= prices[i-1]) {
				value += prices[i] - prices[i-1];
			} else {
				if (value > 0) {
					heap.offer(value);
					if (heap.size() > k) {
						heap.poll();
					}
					value = 0;
				}
			}
		}
		heap.offer(value);
		if (heap.size() > k) {
			heap.poll();
		}
		int ans = 0;
		while (!heap.isEmpty()) {
			ans += heap.poll();
		}
		return ans;
	}


	public static void main(String[] args) {
		int testTimes = 1000;
		int maxSize = 10;
		int maxValue = 10;
		for (int i = 0; i < testTimes; i++) {
//			 int k = (int) (Math.random() * maxSize);
//			 int[] arr = ArrayComparator.generatePositiveRandomArray(maxSize, maxValue);
			int k = 1;
			int[] arr = {3, 3, 7, 6, 10, 5, 1};
			int ans1 = maxProfit(k, arr);
			int ans2 = maxProfit2(k, arr);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				ArrayComparator.printArray(arr);
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("Finish!");
	}
}
