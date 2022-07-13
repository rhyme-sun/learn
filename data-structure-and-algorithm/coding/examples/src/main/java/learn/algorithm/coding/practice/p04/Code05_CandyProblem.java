package learn.algorithm.coding.practice.p04;

/**
 * 题目描述如下：
 * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
 * 你需要按照以下要求，给这些孩子分发糖果：
 * <p>
 * 每个孩子至少分配到 1 个糖果。
 * 相邻两个孩子评分更高的孩子会获得更多的糖果。
 * 请你给每个孩子分发糖果，计算并返回需要准备的最少糖果数目 。
 * <p>
 * 进阶：在上面的条件上增加，相邻的孩子间如果分数一样，分的糖果数必须一样，返回至少需要分多少糖。
 * <p>
 * leetcode: https://leetcode.cn/problems/candy/
 */
public class Code05_CandyProblem {

    static int candy(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] up = upArray(arr);
        int[] down = downArray(arr);
        int candy = 0;
        for (int i = 0; i < up.length; i++) {
            candy += Math.max(up[i], down[i]);
        }
        return candy;
    }

    private static int[] upArray(int[] arr) {
        int[] upArray = new int[arr.length];
        upArray[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1]) {
                upArray[i] = upArray[i - 1] + 1;
            } else {
                upArray[i] = 1;
            }
        }
        return upArray;
    }

    private static int[] downArray(int[] arr) {
        int[] downArray = new int[arr.length];
        downArray[arr.length - 1] = 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                downArray[i] = downArray[i + 1] + 1;
            } else {
                downArray[i] = 1;
            }
        }
        return downArray;
    }

    /**
     * 这是原问题空间优化后的解
     * 时间复杂度O(N)，额外空间复杂度O(1)
     */
    static int candy2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int index = nextMinIndex2(arr, 0);
        int res = rightCands(arr, 0, index++);
        int lbase = 1;
        int next;
        int rcands;
        int rbase;
        while (index != arr.length) {
            if (arr[index] > arr[index - 1]) {
                res += ++lbase;
                index++;
            } else if (arr[index] < arr[index - 1]) {
                next = nextMinIndex2(arr, index - 1);
                rcands = rightCands(arr, index - 1, next++);
                rbase = next - index + 1;
                res += rcands + (rbase > lbase ? -lbase : -rbase);
                lbase = 1;
                index = next;
            } else {
                res += 1;
                lbase = 1;
                index++;
            }
        }
        return res;
    }

    private static int nextMinIndex2(int[] arr, int start) {
        for (int i = start; i != arr.length - 1; i++) {
            if (arr[i] <= arr[i + 1]) {
                return i;
            }
        }
        return arr.length - 1;
    }

    private static int rightCands(int[] arr, int left, int right) {
        int n = right - left + 1;
        return n + n * (n - 1) / 2;
    }

    /**
     * 进阶问题解
     */
    static int candy3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] up = upArray3(arr);
        int[] down = downArray3(arr);
        int candy = 0;
        for (int i = 0; i < up.length; i++) {
            candy += Math.max(up[i], down[i]);
        }
        return candy;
    }

    private static int[] upArray3(int[] arr) {
        int[] upArray = new int[arr.length];
        upArray[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1]) {
                upArray[i] = upArray[i - 1] + 1;
            } else if (arr[i] == arr[i - 1]) {
                upArray[i] = upArray[i - 1];
            } else {
                upArray[i] = 1;
            }
        }
        return upArray;
    }

    private static int[] downArray3(int[] arr) {
        int[] downArray = new int[arr.length];
        downArray[arr.length - 1] = 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                downArray[i] = downArray[i + 1] + 1;
            } else if (arr[i] == arr[i + 1]) {
                downArray[i] = downArray[i + 1];
            } else {
                downArray[i] = 1;
            }
        }
        return downArray;
    }

    /**
     * 这是进阶问题最优解
     * 时间复杂度O(N), 额外空间复杂度O(1)
     */
    static int candy4(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int index = nextMinIndex3(arr, 0);
        int[] data = rightCandsAndBase(arr, 0, index++);
        int res = data[0];
        int lbase = 1;
        int same = 1;
        int next = 0;
        while (index != arr.length) {
            if (arr[index] > arr[index - 1]) {
                res += ++lbase;
                same = 1;
                index++;
            } else if (arr[index] < arr[index - 1]) {
                next = nextMinIndex3(arr, index - 1);
                data = rightCandsAndBase(arr, index - 1, next++);
                if (data[1] <= lbase) {
                    res += data[0] - data[1];
                } else {
                    res += -lbase * same + data[0] - data[1] + data[1] * same;
                }
                index = next;
                lbase = 1;
                same = 1;
            } else {
                res += lbase;
                same++;
                index++;
            }
        }
        return res;
    }

    private static int nextMinIndex3(int[] arr, int start) {
        for (int i = start; i != arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                return i;
            }
        }
        return arr.length - 1;
    }

    private static int[] rightCandsAndBase(int[] arr, int left, int right) {
        int base = 1;
        int cands = 1;
        for (int i = right - 1; i >= left; i--) {
            if (arr[i] == arr[i + 1]) {
                cands += base;
            } else {
                cands += ++base;
            }
        }
        return new int[]{cands, base};
    }

    public static void main(String[] args) {
        int[] test1 = {3, 0, 5, 5, 4, 4, 0};
        System.out.println(candy2(test1));
        System.out.println(candy(test1));

        int[] test2 = {3, 0, 5, 5, 4, 4, 0};
        System.out.println(candy3(test2));
        System.out.println(candy4(test2));
    }
}
