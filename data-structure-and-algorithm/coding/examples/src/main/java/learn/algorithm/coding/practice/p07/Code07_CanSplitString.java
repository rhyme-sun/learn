package learn.algorithm.coding.practice.p07;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 题目描述如下：
 * <p>
 * 给出一个词典字符串 parts，和一个样本字符串 str，返回 str 是否可以全部拆分成词典表里的元素，词典表里的字符串可以重复使用。
 */
public class Code07_CanSplitString {

    static boolean canSplit(String str, String[] parts) {
        if (str == null || str.length() == 0 || parts == null || parts.length == 0) {
            return false;
        }
        Set<String> table = new HashSet<>();
        for (String s : parts) {
            table.add(s);
        }
        return process(str.toCharArray(), 0, table);
    }

    // 来到 index 位置，考虑 index 往后的字符串是否可以全部拆分成词典表里的字符串
    private static boolean process(char[] str, int index, Set<String> table) {
        if (index == str.length) {
            return true;
        }
        boolean ans = false;
        StringBuilder pre = new StringBuilder();
        for (int i = index; i < str.length; i++) {
            pre.append(str[i]);
            if (table.contains(pre.toString())) {
                ans = process(str, i + 1, table);
                if (ans) {
                    break;
                }
            }
        }
        return ans;
    }

    static boolean canSplit2(String str, String[] parts) {
        if (str == null || str.length() == 0 || parts == null || parts.length == 0) {
            return false;
        }
        Set<String> table = new HashSet<>();
        for (String s : parts) {
            table.add(s);
        }
        char[] chars = str.toCharArray();
        int n = chars.length;
        // dp[i] 表示 i 后面字符串是否可以切割成词典表里字符串
        boolean[] dp = new boolean[n + 1];
        dp[n] = true;
        for (int i = n - 1; i >= 0; i--) {
            StringBuilder pre = new StringBuilder();
            for (int j = i; j < n; j++) {
                pre.append(chars[j]);
                if (table.contains(pre.toString())) {
                    dp[i] = dp[j + 1];
                    if (dp[i]) {
                        break;
                    }
                }
            }
        }
        return dp[0];
    }

    // for test
    private static class RandomSample {
        public String str;
        public String[] arr;

        public RandomSample(String s, String[] a) {
            str = s;
            arr = a;
        }
    }

    private static RandomSample generateRandomSample(char[] candidates, int num, int len, int joint) {
        String[] seeds = randomSeeds(candidates, num, len);
        HashSet<String> set = new HashSet<>();
        for (String str : seeds) {
            set.add(str);
        }
        String[] arr = new String[set.size()];
        int index = 0;
        for (String str : set) {
            arr[index++] = str;
        }
        StringBuilder all = new StringBuilder();
        for (int i = 0; i < joint; i++) {
            all.append(arr[(int) (Math.random() * arr.length)]);
        }
        return new RandomSample(all.toString(), arr);
    }

    private static String[] randomSeeds(char[] candidates, int num, int len) {
        String[] arr = new String[(int) (Math.random() * num) + 1];
        for (int i = 0; i < arr.length; i++) {
            char[] str = new char[(int) (Math.random() * len) + 1];
            for (int j = 0; j < str.length; j++) {
                str[j] = candidates[(int) (Math.random() * candidates.length)];
            }
            arr[i] = String.valueOf(str);
        }
        return arr;
    }

    public static void main(String[] args) {
        char[] candidates = {'a', 'b', 'c', 'd', 'e', 'f'};
        int num = 10;
        int len = 4;
        int joint = 5;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            RandomSample sample = generateRandomSample(candidates, num, len, joint);
            boolean ans1 = canSplit(sample.str, sample.arr);
            boolean ans2 = canSplit2(sample.str, sample.arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println(sample.str);
                System.out.println(Arrays.toString(sample.arr));
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
