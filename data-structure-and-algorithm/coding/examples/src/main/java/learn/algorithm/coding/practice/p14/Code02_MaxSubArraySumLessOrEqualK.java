package learn.algorithm.coding.practice.p14;

import java.util.TreeSet;

public class Code02_MaxSubArraySumLessOrEqualK {

	static int getMaxLessOrEqualK(int[] arr, int k) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 记录 i 之前位置的前缀和
		TreeSet<Integer> set = new TreeSet<>();
		set.add(0);
		int sum = 0;
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			// i 位置的前缀和
			sum+=arr[i];
			// 从 i 之前的位置前缀和中找到大于等于 sum-k 的最小值
			Integer ceiling = set.ceiling(sum - k);
			if (ceiling != null) {
				ans = Math.max(ans, sum - ceiling);
			}
			set.add(sum);
		}
		return ans;
	}
}
