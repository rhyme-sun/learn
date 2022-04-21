package learn.algorithm.dp;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 贴纸问题，描述如下：
 * 给定一个字符串 str，一个字符串类型的数组 arr，出现的字符都是小写英文。
 * arr 每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出 str 来。
 * 求返回需要至少多少张贴纸可以完成这个任务，可以重复选择 arr 中的字符串？
 * <p>
 * 比如 str= "babac"，arr = {"ba","c","abcd"}。
 * 如果我们选择 ba + ba + c，那么需要 3 张贴纸可以拼接出字符串 babac；
 * 如果选择 abcd + abcd，那么需要 2 张贴纸；
 * 如果选择 abcd + ba，那么需要 2 张贴纸。所以返回 2。
 * 本题测试链接：https://leetcode.com/problems/stickers-to-spell-word
 */
public class StickersToSpellWord {

    static int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 方法 1，递归方法，选择少的贴纸字符串，使得贴纸的字符串能够完全覆盖目标字符串的所有字符
     *
     * @param stickers 贴纸数组，一个贴纸可以多次使用
     * @param target   目标字符串
     * @return 最小贴纸数量
     */
    private static int process1(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String first : stickers) {
            String rest = minus(target, first);
            if (rest.length() != target.length()) {
                min = Math.min(min, process1(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    /**
     * 去除 target 字符串和 sticker 相同的字符
     *
     * @return target 去除和 sticker 相同字符后剩下的字符
     */
    private static String minus(String target, String sticker) {
        char[] targetChars = target.toCharArray();
        char[] stickerChars = sticker.toCharArray();
        int[] count = new int[26];
        for (char cha : targetChars) {
            count[cha - 'a']++;
        }
        for (char cha : stickerChars) {
            count[cha - 'a']--;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    builder.append((char) (i + 'a'));
                }
            }
        }
        return builder.toString();
    }

    static int minStickers2(String[] stickers, String target) {
        int n = stickers.length;
        // 关键优化（用词频表替代贴纸数组）
        // 贴纸字符串不再用字符串表示，而是用一个长度为 26 数组，数组的每个位置存放其对应字母出现的次数，比如数组 0 位置为 2，表示
        // a 出现了 2 次
        int[][] counts = new int[n][26];
        for (int i = 0; i < n; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }
        int ans = process2(counts, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 使用数组表示贴纸，
     *
     * @param stickers 所有贴纸
     * @param t        目标字串
     * @return 搞定 target 的最少张数
     */
    private static int process2(int[][] stickers, String t) {
        if (t.length() == 0) {
            return 0;
        }
        // target做出词频统计
        // target  aabbc  2 2 1..
        //                0 1 2..
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int n = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            // 尝试第一张贴纸是谁
            int[] sticker = stickers[i];
            // 最关键的优化（重要的剪枝，这一步也是贪心!）
            // 如果该贴纸不包含目标字符串的第一个字符，那么该贴纸一定无效
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int nums = tcounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    static int minStickers3(String[] stickers, String target) {
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int ans = process3(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 方法 3，记忆搜索优化
     */
    private static int process3(int[][] stickers, String t, HashMap<String, Integer> dp) {
        if (dp.containsKey(t)) {
            return dp.get(t);
        }
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int nums = tcounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(t, ans);
        return ans;
    }

    public static void main(String[] args) {
        String[] stickers = new String[]{"ba", "c", "abcd"};
        String target = "babac";
        System.out.println(minStickers1(stickers, target));
        System.out.println(minStickers2(stickers, target));
        System.out.println(minStickers3(stickers, target));
    }
}
