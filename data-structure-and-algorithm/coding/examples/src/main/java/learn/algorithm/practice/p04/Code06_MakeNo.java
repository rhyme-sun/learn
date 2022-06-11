package learn.algorithm.practice.p04;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 问题描述如下：
 * 生成长度为 size 的达标数组，什么叫达标？
 * 达标：对于任意的 `i<k<j`，满足 `[i]+[j]!=[k]*2`，给定一个正数 size，返回长度为 size 的达标数组。
 */
public class Code06_MakeNo {

    static int[] makeNo(int size) {
        if (size == 1) {
            return new int[]{1};
        }
        // 用长度为要求一般的基数组进行构建，左边奇数，右边偶数
        int half = (size + 1) / 2;
        int[] base = makeNo(half);
        int[] ans = new int[size];
        for (int i = 0; i < half; i++) {
            ans[i] = base[i] * 2 - 1;
        }
        for (int i = half; i < size; i++) {
            ans[i] = base[i - half] * 2;
        }
        return ans;
    }

    /**
     * 校验函数
     */
    private static boolean isValid(int[] arr) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int k = i + 1; k < N; k++) {
                for (int j = k + 1; j < N; j++) {
                    if (arr[i] + arr[j] == 2 * arr[k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        for (int N = 1; N < 10; N++) {
            int[] arr = makeNo(N);
            if (!isValid(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("Finish!");
        ArrayComparator.printArray(makeNo(1));
        ArrayComparator.printArray(makeNo(2));
        ArrayComparator.printArray(makeNo(3));
        ArrayComparator.printArray(makeNo(4));
        ArrayComparator.printArray(makeNo(5));
        System.out.println(isValid(makeNo(2981)));

        int a = 4;
        System.out.println("向下取整：" + a / 2);
        System.out.println("向上取整：" + (a + 1) / 2);

    }
}
