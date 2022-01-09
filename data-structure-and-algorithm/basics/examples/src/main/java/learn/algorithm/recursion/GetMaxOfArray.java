package learn.algorithm.recursion;

/**
 * 用递归方法求数组中的最大值
 */
public class GetMaxOfArray {

	/**
	 * 求 arr 中的最大值
	 */
	public static int getMax(int[] arr) {
		return process(arr, 0, arr.length - 1);
	}

	/**
	 * 在 arr[l..r]范围上求最大值
	 */
	public static int process(int[] arr, int l, int r) {
		// arr[l..r] 范围上只有一个数，直接返回，递归终止条件，子问题分解边界
		if (l == r) {
			return arr[l];
		}
		int mid = l + ((r - l) >> 1);
		int leftMax = process(arr, l, mid);
		int rightMax = process(arr, mid + 1, r);
		return Math.max(leftMax, rightMax);
	}
}
