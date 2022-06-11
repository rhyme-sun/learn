package learn.algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回一个字符串不同切割方法下的不相交子串。
 * 比如，abc，切割情况数有：不切割 abc，切割成 a 和 bc，切割成 ab 和 c，切割成 a，b，c。
 */
public class SplitSubstrings {

    static List<List<String>> splitSubstrings(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        List<List<String>> splitSubstrings = new ArrayList<>();
        process(str.toCharArray(), 0, new ArrayList<>(), splitSubstrings);
        return splitSubstrings;
    }

    /**
     * 来到 index 位置，考虑要不要在 index 后面来一刀，
     *
     * @param splitPos 存放切割位置
     */
    private static void process(char[] str, int index, List<Integer> splitPos, List<List<String>> splitSubstrings) {
        if (index == str.length - 1) {
            splitSubstrings.add(splitString(str, splitPos));
            return;
        }
        // 不切
        process(str, index + 1, splitPos, splitSubstrings);
        // 切
        splitPos.add(index);
        process(str, index + 1, splitPos, splitSubstrings);
        splitPos.remove(splitPos.size() - 1);
    }

    /**
     * 根据切割位置构建切割字符串
     */
    private static List<String> splitString(char[] str, List<Integer> splitPos) {
        List<String> splitStrings = new ArrayList<>();
        if (splitPos.size() == 0) {
            splitStrings.add(new String(str));
        } else {
            int prePos = 0;
            for (Integer pos : splitPos) {
                splitStrings.add(new String(str, prePos, pos - prePos + 1));
                prePos = pos + 1;
            }
            // 注意：最后一个切割位置到结尾部分的字符串
            splitStrings.add(new String(str, prePos, str.length - prePos));
        }

        return splitStrings;
    }

    public static void main(String[] args) {
        String str1 = "";
        String str2 = "a";
        String str3 = "ab";
        String str4 = "abc";

        System.out.println(splitSubstrings(str1));
        System.out.println(splitSubstrings(str2));
        System.out.println(splitSubstrings(str3));
        System.out.println(splitSubstrings(str4));
    }
}
