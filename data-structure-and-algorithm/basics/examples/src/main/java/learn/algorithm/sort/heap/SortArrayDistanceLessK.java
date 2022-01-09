package learn.algorithm.sort.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

import learn.algorithm.sort.SortTestUtils;

/**
 * 已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离一定不超过 k，并且 k 相对于数组长度来说是比较小的。
 */
public class SortArrayDistanceLessK {

	public static void sortedArrDistanceLessK(int[] arr, int k) {
		if (k == 0) {
			return;
		}
		// 默认小根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		int index = 0;
		// 0...k-1
		for (; index <= Math.min(arr.length - 1, k - 1); index++) {
			heap.add(arr[index]);
		}
		int i = 0;
		for (; index < arr.length; i++, index++) {
			heap.add(arr[index]);
			arr[i] = heap.poll();
		}
		while (!heap.isEmpty()) {
			arr[i++] = heap.poll();
		}
	}

	// for test
	private static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int K) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		// 先排个序
		Arrays.sort(arr);
		// 然后开始随意交换，但是保证每个数距离不超过K
		// swap[i] == true, 表示i位置已经参与过交换
		// swap[i] == false, 表示i位置没有参与过交换
		boolean[] isSwap = new boolean[arr.length];
		for (int i = 0; i < arr.length; i++) {
			int j = Math.min(i + (int) (Math.random() * (K + 1)), arr.length - 1);
			if (!isSwap[i] && !isSwap[j]) {
				isSwap[i] = true;
				isSwap[j] = true;
				int tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
			}
		}
		return arr;
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int k = (int) (Math.random() * maxSize) + 1;
			int[] arr = randomArrayNoMoveMoreK(maxSize, maxValue, k);
			int[] arr1 = SortTestUtils.copyArray(arr);
			int[] arr2 = SortTestUtils.copyArray(arr);
			sortedArrDistanceLessK(arr1, k);
			SortTestUtils.comparator(arr2);
			if (!SortTestUtils.isEqual(arr1, arr2)) {
				succeed = false;
				System.out.println("K : " + k);
				SortTestUtils.printArray(arr);
				SortTestUtils.printArray(arr1);
				SortTestUtils.printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Oops!");
		int k = (int) (Math.random() * maxSize) + 1;
		int[] arr = randomArrayNoMoveMoreK(maxSize, maxValue, k);
		SortTestUtils.printArray(arr);
		sortedArrDistanceLessK(arr, k);
		SortTestUtils.printArray(arr);
	}
}