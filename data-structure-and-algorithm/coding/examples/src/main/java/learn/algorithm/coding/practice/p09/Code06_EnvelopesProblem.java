package learn.algorithm.coding.practice.p09;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 问题描述如下：
 * 给你一个二维整数数组 envelopes ，其中 `envelopes[i] = [wi, hi]` ，表示第 i 个信封的宽度和高度。
 * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 * 请计算最多能有多少个信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
 * <p>
 * leetcode: https://leetcode.cn/problems/russian-doll-envelopes
 */
public class Code06_EnvelopesProblem {

    static int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0 || envelopes[0].length == 0) {
            return 0;
        }
        Envelope[] ens = buildEnvelopes(envelopes);
        Arrays.sort(ens, new EnvelopeComparator());
        return lengthOfLIS(ens);
    }

    private static Envelope[] buildEnvelopes(int[][] envelopes) {
        Envelope[] ens = new Envelope[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            ens[i] = new Envelope(envelopes[i][0], envelopes[i][1]);
        }
        return ens;
    }

    // 按高度求最长递增子序列的长度
    private static int lengthOfLIS(Envelope[] envelopes) {
        // ends[i] 表示递增子序列长度为 i+1 的最小值
        int[] ends = new int[envelopes.length];
        ends[0] = envelopes[0].height;
        // ends 最后一个值的下标
        int end = 0;
        for (int i = 1; i < envelopes.length; i++) {
            int floor = floor(ends, 0, end, envelopes[i].height);
            ends[floor + 1] = envelopes[i].height;
            end = Math.max(end, floor + 1);
        }
        return end + 1;
    }

    // 从 ends 中找到小于 value 的最大值对应下标，找不到返回 -1
    private static int floor(int[] ends, int l, int r, int value) {
        int index = -1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (ends[mid] < value) {
                l = mid + 1;
                index = mid;
            } else {
                r = mid - 1;
            }
        }
        return index;
    }

    private static class Envelope {
        private int weight;
        private int height;

        Envelope(int w, int h) {
            weight = w;
            height = h;
        }
    }

    private static class EnvelopeComparator implements Comparator<Envelope> {

        @Override
        public int compare(Envelope o1, Envelope o2) {
            return o1.weight == o2.weight ? -(o1.height - o2.height) : o1.weight - o2.weight;
        }
    }

    public static void main(String[] args) {
       // int[][] envelopes = {{5, 4}, {6, 4}, {6, 7}, {2, 3}};
        int[][] envelopes = {{1, 1}, {1, 1}, {1, 1}};
        int ans = maxEnvelopes(envelopes);
        System.out.println(ans);
    }
}
