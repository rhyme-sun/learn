package learn.algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印字符串的全排序
 */
public class PrintAllPermutations {

    /**
     * 方法 1，使用排列剩下字符思路进行递归
     */
    static List<String> permutation1(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        ArrayList<Character> rest = new ArrayList<>();
        for (char cha : str) {
            rest.add(cha);
        }
        String path = "";
        f(rest, path, ans);
        return ans;
    }

    /**
     * 字符排列递归函数，排列剩下的字符
     *
     * @param rest 剩下没有排列的字符
     * @param path 已经排列字符得到的结果
     * @param ans  存放字符串全排列结果
     */
    private static void f(ArrayList<Character> rest, String path, List<String> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
        } else {
            int n = rest.size();
            for (int i = 0; i < n; i++) {
                char cur = rest.get(i);
                // 选择某个字符，排列剩下的字符
                rest.remove(i);
                f(rest, path + cur, ans);
                // 恢复现场，必须需要恢复现场，因为下次迭代需要完整地剩余数组
                rest.add(i, cur);
            }
        }
    }

    /**
     * 方法 2，使用某个位置使用那个字符的思路进行递归
     */
    static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g1(str, 0, ans);
        return ans;
    }

    /**
     * 递归安排 index 位置地字符
     *
     * @param str   字符数组
     * @param index 来到了 char[index] 字符地位置
     * @param ans   存放字符串全排列结果
     */
    private static void g1(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            for (int i = index; i < str.length; i++) {
                // 第 i 个位置需要放置地字符
                swap(str, index, i);
                // 在 i 位置字符决定后，递归决定 i+1 位置地字符
                g1(str, index + 1, ans);
                // 恢复现场
                swap(str, index, i);
            }
        }
    }

    /**
     * 方法 2，使用某个位置使用那个字符的思路进行递归，并且去除重复的排列
     */
    static List<String> permutation3(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g2(str, 0, ans);
        return ans;
    }

    private static void g2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            // 记录某个字符是否已经迭代过，256 表示字符编码值的范围
            // 减支
            boolean[] visited = new boolean[256];
            for (int i = index; i < str.length; i++) {
                // 字符编码值
                int a = str[i];
                if (!visited[a]) {
                    visited[str[i]] = true;
                    swap(str, index, i);
                    g2(str, index + 1, ans);
                    swap(str, index, i);
                }
            }
        }
    }

    private static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    public static void main(String[] args) {
        String s = "acc";
        List<String> ans1 = permutation1(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans2 = permutation2(s);
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans3 = permutation3(s);
        for (String str : ans3) {
            System.out.println(str);
        }
    }
}
