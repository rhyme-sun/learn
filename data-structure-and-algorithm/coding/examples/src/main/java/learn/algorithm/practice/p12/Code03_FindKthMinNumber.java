package learn.algorithm.practice.p12;

/**
 * 找到两个长度为 N 的有序数组第 N 大的数，`O(logN)`。
 * 进阶问题：在两个都有序的数组中找整体第 K 小的数，可以做到 `O(log(Min(M,N)))`。
 * <p>
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数 。
 * 算法的时间复杂度应该为 `O(log(m+n))` 。
 * 链接：https://leetcode.cn/problems/median-of-two-sorted-arrays
 */
public class Code03_FindKthMinNumber {

    // nums1 和 nums2 求中位数
    static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size = nums1.length + nums2.length;
        boolean even = (size & 1) == 0;
        if (nums1.length != 0 && nums2.length != 0) {
            if (even) {
                return (double) (findKthNum(nums1, nums2, size / 2) + findKthNum(nums1, nums2, size / 2 + 1)) / 2D;
            } else {
                return findKthNum(nums1, nums2, size / 2 + 1);
            }
        } else if (nums1.length != 0) {
            if (even) {
                return (double) (nums1[(size - 1) / 2] + nums1[size / 2]) / 2;
            } else {
                return nums1[size / 2];
            }
        } else if (nums2.length != 0) {
            if (even) {
                return (double) (nums2[(size - 1) / 2] + nums2[size / 2]) / 2;
            } else {
                return nums2[size / 2];
            }
        } else {
            return 0;
        }
    }

    // 找 arr1 和 arr2 整体上第 k 小的数
    // O(log(Min(M,N)))
    static int findKthNum(int[] arr1, int[] arr2, int k) {
        int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
        int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
        int l = longs.length;
        int s = shorts.length;
        if (k <= s) {
            return getUpMedian(shorts, 0, k - 1, longs, 0, k - 1);
        }
        if (k > l) {
            if (shorts[k - l - 1] >= longs[l - 1]) {
                return shorts[k - l - 1];
            }
            if (longs[k - s - 1] >= shorts[s - 1]) {
                return longs[k - s - 1];
            }
            return getUpMedian(shorts, k - l, s - 1, longs, k - s, l - 1);
        }
        if (longs[k - s - 1] >= shorts[s - 1]) {
            return longs[k - s - 1];
        }
        return getUpMedian(shorts, 0, s - 1, longs, k - s, k - 1);
    }


    // 求两个等长数组 [l1,r1] 和 [l2,r2] 范围整体上的上中位数
    // O(log(N))
    static int getUpMedian(int[] arr1, int l1, int r1, int[] arr2, int l2, int r2) {
        while (l1 < r1) {
            int mid1 = (l1 + r1) / 2;
            int mid2 = (l2 + r2) / 2;
            if (arr1[mid1] == arr2[mid2]) {
                return arr1[mid1];
            }
            if (((r1 - l1 + 1) & 1) == 1) {
                if (arr1[mid1] > arr2[mid2]) {
                    if (arr2[mid2] >= arr1[mid1 - 1]) {
                        return arr2[mid2];
                    }
                    r1 = mid1 - 1;
                    l2 = mid2 + 1;
                } else {
                    if (arr1[mid1] >= arr2[mid2 - 1]) {
                        return arr1[mid1];
                    }
                    r2 = mid2 - 1;
                    l1 = mid1 + 1;
                }
            } else {
                if (arr1[mid1] > arr2[mid2]) {
                    r1 = mid1;
                    l2 = mid2 + 1;
                } else {
                    r2 = mid2;
                    l1 = mid1 + 1;
                }
            }
        }
        return Math.min(arr1[l1], arr2[l2]);
    }


    public static void main(String[] args) {
        test1();
//        test2();
    }

    private static void test1() {
        int[] arr1 = new int[]{1, 3, 5, 7};
        int[] arr2 = new int[]{2, 4, 6, 8};
        int ans1 = getUpMedian(arr1, 0, arr1.length - 1, arr2, 0, arr2.length - 1);
        System.out.println(ans1);

        arr1 = new int[]{1, 3, 5, 7, 9};
        arr2 = new int[]{2, 4, 6, 8, 10};

        int ans2 = getUpMedian(arr1, 0, arr1.length - 1, arr2, 0, arr2.length - 1);
        System.out.println(ans2);

        arr1 = new int[]{1, 2, 3};
        arr2 = new int[]{4, 5, 6};
        int ans3 = getUpMedian(arr1, 0, arr1.length - 1, arr2, 0, arr2.length - 1);
        System.out.println(ans3);
    }

    private static void test2() {
        int[] arr1 = new int[]{1, 2, 3};
        int[] arr2 = new int[]{1, 2};
        int ans = findKthNum(arr1, arr2, 3);
        System.out.println(ans);
    }
}
