package learn.algorithm.basics.algorithm.resursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串的全排序
 */
public class Code03_PrintAllPermutations {

    static List<String> permutation(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        process1(str, 0, ans);
        return ans;
    }

    /**
     * 递归安排 index 位置地字符
     *
     * @param str   字符数组
     * @param index 来到了 char[index] 字符地位置
     * @param ans   存放字符串全排列结果
     */
    private static void process1(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            for (int i = index; i < str.length; i++) {
                // 第 i 个位置需要放置地字符
                swap(str, index, i);
                // 在 i 位置字符决定后，递归决定 i+1 位置地字符
                process1(str, index + 1, ans);
                // 恢复现场
                swap(str, index, i);
            }
        }
    }

    // 收集字符串全排列，去除重复
    static List<String> permutationNoRepeat(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return ans;
        }
        process2(str.toCharArray(), 0, ans);
        return ans;
    }

    /**
     * 考虑当前来到 index 字符，前面的字符已经选择完毕，index 后的字符怎么选择。
     */
    private static void process2(char[] chars, int index, List<String> ans) {
        if (index == chars.length) {
            ans.add(String.valueOf(chars));
            return;
        }
        boolean[] visited = new boolean[256];
        for (int i = index; i < chars.length; i++) {
            // 剪枝
            if (!visited[chars[i]]) {
                visited[chars[i]] = true;
                swap(chars, index, i);
                process2(chars, index + 1, ans);
                swap(chars, i, index);
            }
        }
    }

    private static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    public static void main(String[] args) {
        String s = "aaa";
        List<String> ans1 = permutation(s);
        List<String> ans2 = permutationNoRepeat(s);
        System.out.println(ans1);
        System.out.println(ans2);

        List<Integer> ans = new ArrayList<>(1);
        ans.add(0, 2);
        ans.add(0, 2);
        ans.add(0, 2);
        ans.set(0, 1);
        System.out.println(ans);
    }
}
