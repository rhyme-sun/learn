package learn.algorithm.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 打印字符串全部子序列
 */
public class PrintAllSubsequences {

    static List<String> subs(String s) {
        char[] str = s.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        process(str, 0, path, ans);
        return ans;
    }

    /**
     * 打印字符串子序列递归函数
     *
     * @param str   字符串字符数组
     * @param index 来到了 char[index] 字符，index 是位置
     * @param path  到 char[index] 已经组成的路径
     * @param ans   答案
     */
    private static void process(char[] str, int index, String path, List<String> ans) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        // 子序列组成不需要 index 位置的字符
        process(str, index + 1, path, ans);
        // 子序列组成需要 index 位置的字符
        process(str, index + 1, path + str[index], ans);
    }

    static List<String> subsNoRepeat(String s) {
        char[] str = s.toCharArray();
        String path = "";
        HashSet<String> set = new HashSet<>();
        process(str, 0, path, set);
        return new ArrayList<>(set);
    }

    private static void process(char[] str, int index, String path, HashSet<String> ans) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        process(str, index + 1, path, ans);
        process(str, index + 1, path + str[index], ans);
    }

    public static void main(String[] args) {
        String test = "acccc";
        List<String> ans1 = subs(test);
        List<String> ans2 = subsNoRepeat(test);

        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=================");
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=================");
    }
}
