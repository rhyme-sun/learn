package learn.algorithm.recursion;

/**
 * 给定一个字符串，返回切割成不相交子串的切割方法数量
 * 比如，abc，切割方法数有：不切割，切割成 a 和 bc，切割成 ab 和 c，切割成 a，b，c 一共 4 中。
 */
public class SplitWays {

    // 切割方法数为 2^n-1
    static int ways(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    // 当前来到 index 位置，考虑要不要在 index 后面来一刀，返回切割方法数
    private static int process(char[] str, int index) {
        // 来到了末尾，后面没有数了，得到一种切割方法
        if (index == str.length - 1) {
            return 1;
        }
        return process(str, index + 1) + process(str, index + 1);
    }

    static int ways2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        //return (int) Math.pow(2, str.length() - 1);
        return 1 << (str.length() - 1);
    }

    public static void main(String[] args) {
        String str0 = "";
        String str1 = "a";
        String str2 = "ab";
        String str3 = "abc";
        String str4 = "abcd";
        System.out.println(ways(str0));
        System.out.println(ways2(str0));
        System.out.println(ways(str1));
        System.out.println(ways2(str1));
        System.out.println(ways(str2));
        System.out.println(ways2(str2));
        System.out.println(ways(str3));
        System.out.println(ways2(str3));
        System.out.println(ways(str4));
        System.out.println(ways2(str4));
    }
}
