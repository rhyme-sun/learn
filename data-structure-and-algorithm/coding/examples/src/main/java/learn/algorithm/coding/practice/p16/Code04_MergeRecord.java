package learn.algorithm.coding.practice.p16;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 给定整数 power，给定一个数组 arr，给定一个数组 reverse。含义如下：
 * arr 的长度一定是 2 的 power 次方，reverse 中的每个值一定都在 0~power 范围。
 * 例如 power = 2, arr = {3, 1, 4, 2}，reverse = {0, 1, 0, 2}
 * 任何一个在前的数字可以和任何一个在后的数组，构成一对数。可能是升序关系、相等关系或者降序关系。
 * 比如 arr 开始时有如下的降序对：(3,1)、(3,2)、(4,2)，一共 3 个。
 * 接下来根据 reverse 对 arr 进行调整：
 * reverse[0] = 0, 表示在 arr 中，划分每 1（2 的 0 次方）个数一组，然后每个小组内部逆序，那么 arr 变成 [3,1,4,2]，此时有 3 个逆序对；
 * reverse[1] = 1, 表示在 arr 中，划分每 2（2 的 1 次方）个数一组，然后每个小组内部逆序，那么 arr 变成 [1,3,2,4]，此时有 1 个逆序对；
 * reverse[2] = 0, 表示在 arr 中，划分每 1（2 的 0 次方）个数一组，然后每个小组内部逆序，那么 arr 变成 [1,3,2,4]，此时有 1个逆序对。
 * reverse[3] = 2, 表示在 arr 中，划分每 4（2 的 2 次方）个数一组，然后每个小组内部逆序，那么 arr 变成 [4,2,3,1]，此时有 5 个逆序对。
 * 所以返回 [3,1,1,5]，表示每次调整之后的逆序对数量。
 * <p>
 * 输入数据状况：
 * power 的范围 [0,20]
 * arr 长度范围 [1 x 10 的 7 次方]
 * reverse 长度范围[ 1 x 10 的 6 次方]
 */
public class Code04_MergeRecord {

    static int[] reversePair(int[] arr, int[] reverse, int power) {
        if (arr == null || arr.length == 0 || reverse == null || reverse.length == 0) {
            return null;
        }
        int n = reverse.length;


        // 逆序数组，统计逆序对的个数，就是升序对的个数
        int[] reverseArr = reverseArray(arr);
        int[] pair = new int[n];
        process(reverseArr, 0, reverseArr.length - 1, power, pair);

        // reversePair[i] 表示 2^i 一组逆序对的个数
        int[] reversePair = new int[n];
        process(arr, 0, arr.length - 1, power, reversePair);

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            // 2^j 一组进行交换
            int times = reverse[i];
            for (int j = 0; j <= times; j++) {
                int tmp = pair[j];
                pair[j] = reversePair[j];
                reversePair[j] = tmp;
            }
            for (int count : reversePair) {
                ans[i] += count;
            }
        }
        return ans;
    }

    // 在 [l,r] 范围上归并排序，并统计逆序对的数量
    private static void process(int[] arr, int l, int r, int power, int[] reversePair) {
        if (l == r) {
            return;
        }
        int m = l + ((r - l) >> 1);
        process(arr, l, m, power - 1, reversePair);
        process(arr, m + 1, r, power - 1, reversePair);
        reversePair[power] += merge(arr, l, m, r);
    }

    // 归并左右两组，并返回左右两组逆序对个数
    private static int merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = m + 1;
        int ans = 0;
        while (p1 <= m && p2 <= r) {
            if (arr[p1] > arr[p2]) {
                ans += m - p1 + 1;
            }
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[index++] = arr[p1++];
        }
        while (p2 <= r) {
            help[index++] = arr[p2++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return ans;
    }

    private static int[] reverseArray(int[] arr) {
        int n = arr.length;
        int[] reverse = new int[n];
        // i + j = n - 1
        // j = n - 1 - i
        for (int i = n - 1; i >= 0; i--) {
            reverse[n - 1 - i] = arr[i];
        }
        return reverse;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 2};
        int[] reverse = {0, 1, 0, 2};
        int power = 2;
        int[] ans = reversePair(arr, reverse, power);
        ArrayComparator.printArray(ans);
    }
}
