package learn.algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印字符串的全排序
 */
public class PrintAllPermutations {

    static List<String> permutation(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return ans;
        }
        process(str.toCharArray(), 0, ans);
        return ans;
    }

    /**
     * 考虑当前来到 index 字符，前面的字符已经选择完毕，index 后的字符怎么选择。
     */
    private static void process(char[] chars, int index, List<String> ans) {
        if (index == chars.length) {
            ans.add(String.valueOf(chars));
            return;
        }
        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            process(chars, index + 1, ans);
            swap(chars, i, index);
        }
    }

    private static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    public static void main(String[] args) {
        String s = "abd";
        List<String> ans1 = permutation(s);
        for (String str : ans1) {
            System.out.println(str);
        }
    }
}
