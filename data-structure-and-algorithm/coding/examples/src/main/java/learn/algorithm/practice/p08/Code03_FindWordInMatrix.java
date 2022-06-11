package learn.algorithm.practice.p08;

/**
 * 题目描述如下：
 * <p>
 * 给定一个char[][] matrix，也就是char类型的二维数组，再给定一个字符串word，
 * 可以从任何一个某个位置出发，可以走上下左右，能不能找到word？
 * 比如：
 * char[][] m = {
 * { 'a', 'b', 'z' },
 * { 'c', 'd', 'o' },
 * { 'f', 'e', 'o' },
 * };
 * word = "zooe"
 * 是可以找到的
 * <p>
 * 设定1：可以走重复路的情况下，返回能不能找到
 * 比如，word = "zoooz"，是可以找到的，z -> o -> o -> o -> z，因为允许走一条路径中已经走过的字符
 * <p>
 * 设定2：不可以走重复路的情况下，返回能不能找到
 * 比如，word = "zoooz"，是不可以找到的，因为允许走一条路径中已经走过的字符不能重复走
 * <p>
 * 写出两种设定下的code
 */
public class Code03_FindWordInMatrix {

    static boolean findWord1(char[][] matrix, String word) {
        if (word == null || word.length() == 0) {
            return true;
        }
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int n = matrix.length;
        int m = matrix[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                boolean ans = process1(matrix, word.toCharArray(), i, j, 0);
                if (ans) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 来到矩阵的 (i,j) 位置，看能不能组成 word[index...] 后字符串
     */
    private static boolean process1(char[][] matrix, char[] word, int i, int j, int index) {
        if (index == word.length) {
            return true;
        }
        if (matrix[i][j] != word[index]) {
            return false;
        }
        // 向上找
        boolean p1 = i > 0 ? process1(matrix, word, i - 1, j, index + 1) : false;
        // 向下找
        boolean p2 = i < matrix.length - 1 ? process1(matrix, word, i + 1, j, index + 1) : false;
        // 向左找
        boolean p3 = j > 0 ? process1(matrix, word, i, j - 1, index + 1) : false;
        // 向右找
        boolean p4 = j < matrix[0].length - 1 ? process1(matrix, word, i, j + 1, index + 1) : false;
        return p1 || p2 || p3 || p4;
    }

    /**
     * 方法一的严格递推优化
     */
    static boolean findWord2(char[][] m, String word) {
        if (word == null || word == "") {
            return true;
        }
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return false;
        }
        char[] w = word.toCharArray();
        int N = m.length;
        int M = m[0].length;
        int len = w.length;
        // dp[i][j][k]表示：必须以m[i][j]这个字符结尾的情况下，能不能找到w[0...k]这个前缀串
        boolean[][][] dp = new boolean[N][M][len];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = m[i][j] == w[0];
            }
        }
        for (int k = 1; k < len; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    dp[i][j][k] = (m[i][j] == w[k] && checkPrevious(dp, i, j, k));
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (dp[i][j][len - 1]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkPrevious(boolean[][][] dp, int i, int j, int k) {
        boolean up = i > 0 ? (dp[i - 1][j][k - 1]) : false;
        boolean down = i < dp.length - 1 ? (dp[i + 1][j][k - 1]) : false;
        boolean left = j > 0 ? (dp[i][j - 1][k - 1]) : false;
        boolean right = j < dp[0].length - 1 ? (dp[i][j + 1][k - 1]) : false;
        return up || down || left || right;
    }

    /**
     * 不可以走重复路
     */
    static boolean findWord3(char[][] matrix, String word) {
        if (word == null || word.equals("")) {
            return true;
        }
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        char[] w = word.toCharArray();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (process2(matrix, w, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 从 m[i][j] 这个字符出发，能不能找到 word[index...] 这个后缀串
     */
    private static boolean process2(char[][] m, char[] word, int i, int j, int index) {
        if (index == word.length) {
            return true;
        }
        if (i == -1 || i == m.length || j == -1 || j == m[0].length) {
            return false;
        }
        if (m[i][j] != word[index]) {
            return false;
        }
        // 走过了
        if (m[i][j] == 0) {
            return false;
        }
        // 0 表示走过了
        m[i][j] = 0;
        boolean ans = false;
        if (process2(m,  word,i + 1, j, index + 1) || process2(m, word, i - 1, j, index + 1) ||
                process2(m,  word,i, j + 1, index + 1) || process2(m, word, i, j - 1, index + 1)) {
            ans = true;
        }
        m[i][j] = word[index];
        return ans;
    }

    public static void main(String[] args) {
        char[][] m = {{'a', 'b', 'z'}, {'c', 'd', 'o'}, {'f', 'e', 'o'},};
        String word1 = "zoooz";
        // 可以走重复路的设定
        System.out.println(findWord1(m, word1));
        System.out.println(findWord2(m, word1));
        System.out.println();

        // 不可以走重复路的设定
        System.out.println(findWord2(m, word1));
        System.out.println(findWord3(m, word1));
    }
}
