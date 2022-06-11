package learn.algorithm.advance.algorithm.array;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定一个整数组成的无序数组 arr，值可能正、可能负、可能 0。找到 arr 中累加和小于等于 K 且长度最大的子数组，返回其长度。
 */
public class Code03_LongestLessSumSubArrayLength {

    static int maxLengthAwesome(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[][] minSum = minSum(arr);
        int[] minSums = minSum[0];
        int[] minSumEnds = minSum[1];
        // 迟迟扩不进来那一块儿的开头位置
        int end = 0;
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            while (end < arr.length && sum + minSums[end] <= k) {
                sum += minSums[end];
                end = minSumEnds[end] + 1;
            }
            // while 循环结束之后：
            // 1) 如果以 i 开头的情况下，累加和 <=k 的最长子数组是 arr[i..end-1]，看看这个子数组长度能不能更新 res；
            // 2) 如果以 i 开头的情况下，累加和 <=k 的最长子数组比 arr[i..end-1] 短，更新还是不更新 res 都不会影响最终结果；
            ans = Math.max(ans, end - i);
            // window L:i R:end-1
            if (end > i) {
                // 这里不用去更新 res，因为一定不是最优解
                sum -= arr[i];
            } else { // i == end，窗口维持不住了 end 右移
                end = i + 1;
            }
        }
        return ans;
    }

    private static int[][] minSum(int[] arr) {
        int[] minSums = new int[arr.length];
        int[] minSumEnds = new int[arr.length];
        // 从后往前构建
        minSums[arr.length - 1] = arr[arr.length - 1];
        minSumEnds[arr.length - 1] = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (minSums[i + 1] < 0) {
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            } else {
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }
        return new int[][]{minSums, minSumEnds};
    }

    static int maxLength(int[] arr, int k) {
        // h[i] 表示以 0 开头到 i-1 位置，最大的前缀和，顺序一定是从小到大的
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre;
        int len;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            // 0~i 累加和减去 0~j（j<i） 中某个位置的前缀和，如果值小于等于 K，则说明 j~i 位置的子数组满足要求
            // j 越小，满足要求的子数组就越长，怎么找到最小的 j

            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    /**
     * 从数组 h 获得大于等于 num 的最小值的位置 O(logN)
     */
    private static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000000; i++) {
            int[] arr = ArrayComparator.generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLengthAwesome(arr, k) != maxLength(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("Finish!");
    }
}
