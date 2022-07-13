package learn.algorithm.coding.practice.p13;

/**
 * 使用下面描述的算法可以扰乱字符串 s 得到字符串 t ：
 * 如果字符串的长度为 1 ，算法停止
 * 如果字符串的长度 > 1 ，执行下述步骤：
 * 在一个随机下标处将字符串分割成两个非空的子字符串。即，如果已知字符串 s ，则可以将其分成两个子字符串 x 和 y ，且满足 s = x + y 。
 * 随机决定是要「交换两个子字符串」还是要「保持这两个子字符串的顺序不变」。即，在执行这一步骤之后，s 可能是 s = x + y 或者 s = y + x 。
 * 在 x 和 y 这两个子字符串上继续从步骤 1 开始递归执行此算法。
 * 给你两个 长度相等 的字符串 s1 和 s2，判断 s2 是否是 s1 的扰乱字符串。如果是，返回 true ；否则，返回 false 。
 * <p>
 * 链接：https://leetcode.cn/problems/scramble-string
 */
public class Code02_ScrambleString {

    static boolean isScramble(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        return process(str1, str2, 0, 0, str1.length);
    }

    // 考虑 str1[i..i+size-1] str2[j...j+size-1] 是否互为扰乱字符串
    // size 表示字符串长度
    // i 和 j 分别表示 str1 和 str2 的起始位置
    private static boolean process(char[] str1, char[] str2, int i, int j, int size) {
        if (size == 1) {
            return str1[i] == str2[j];
        }
        // 枚举切割位置
        // cut=1 表示将 str1 切割为 1 | size-1 两个部分
        for (int cut = 1; cut < size; cut++) {
            // a | b  c  d  e
            // a'| b' c' d' e'
            boolean p1 = process(str1, str2, i, j, cut) && process(str1, str2, i + cut, j + cut, size - cut);
            // a | b  c  d  e
            // a'  b' c' d'|e'  size=5 cut=1
            // j            j+4 j+size-cut
            boolean p2 = process(str1, str2, i, j + size - cut, cut) && process(str1, str2, i + cut, j, size - cut);
            if (p1 || p2) {
                return true;
            }
        }
        return false;
    }

    static boolean isScramble2(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        // dp[i][j] 0 表示未计算 1 表示 true -1 false
        int[][][] dp = new int[str1.length][str2.length][str1.length + 1];
        return process2(str1, str2, 0, 0, str1.length, dp);
    }

    // 考虑 str1[i..i+size-1] str2[j...j+size-1] 是否互为扰乱字符串
    // size 表示字符串长度
    // i 和 j 分别表示 str1 和 str2 的起始位置
    private static boolean process2(char[] str1, char[] str2, int i, int j, int size, int[][][] dp) {
        if (dp[i][j][size] != 0) {
            return dp[i][j][size] == 1;
        }
        boolean ans = false;
        if (size == 1) {
            ans = str1[i] == str2[j];
        } else {
            for (int cut = 1; cut < size; cut++) {
                boolean p1 = process2(str1, str2, i, j, cut, dp) &&
                        process2(str1, str2, i + cut, j + cut, size - cut, dp);
                boolean p2 = process2(str1, str2, i, j + size - cut, cut, dp) &&
                        process2(str1, str2, i + cut, j, size - cut, dp);
                if (p1 || p2) {
                    ans = true;
                    break;
                }
            }
        }
        dp[i][j][size] = ans ? 1 : -1;
        return ans;
    }

    private static boolean sameTypeSameNumber(char[] str1, char[] str2) {
        if (str1.length != str2.length) {
            return false;
        }
        int[] map = new int[256];
        for (int i = 0; i < str1.length; i++) {
            map[str1[i]]++;
        }
        for (int i = 0; i < str2.length; i++) {
            if (--map[str2[i]] < 0) {
                return false;
            }
        }
        return true;
    }

    static boolean isScramble3(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        int N = s1.length();
        boolean[][][] dp = new boolean[N][N][N + 1];
        for (int L1 = 0; L1 < N; L1++) {
            for (int L2 = 0; L2 < N; L2++) {
                dp[L1][L2][1] = str1[L1] == str2[L2];
            }
        }
        // 第一层for循环含义是：依次填size=2层、size=3层..size=N层，每一层都是一个二维平面
        // 第二、三层for循环含义是：在具体的一层，整个面都要填写，所以用两个for循环去填一个二维面
        // L1的取值氛围是[0,N-size]，因为从L1出发往右长度为size的子串，L1是不能从N-size+1出发的，这样往右就不够size个字符了
        // L2的取值范围同理
        // 第4层for循环完全是递归函数怎么写，这里就怎么改的
        for (int size = 2; size <= N; size++) {
            for (int L1 = 0; L1 <= N - size; L1++) {
                for (int L2 = 0; L2 <= N - size; L2++) {
                    for (int leftPart = 1; leftPart < size; leftPart++) {
                        if ((dp[L1][L2][leftPart] && dp[L1 + leftPart][L2 + leftPart][size - leftPart])
                                || (dp[L1][L2 + size - leftPart][leftPart] && dp[L1 + leftPart][L2][size - leftPart])) {
                            dp[L1][L2][size] = true;
                            break;
                        }
                    }
                }
            }
        }
        return dp[0][0][N];
    }

    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "cba";
        boolean ans = isScramble(s1, s2);
        System.out.println(ans);
    }
}
