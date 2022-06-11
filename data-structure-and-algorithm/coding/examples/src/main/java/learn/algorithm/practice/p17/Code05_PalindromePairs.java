package learn.algorithm.practice.p17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 给定一个字符串数组 arr，里面都是互不相同的单词，找出所有不同的索引对 `(i,j)`，使得列表中的两个单词，`words[i]+words[j]`，可拼接成回文串。
 * <p>
 * https://leetcode.cn/problems/palindrome-pairs/
 */
public class Code05_PalindromePairs {

    static List<List<Integer>> palindromePairs(String[] words) {
        Map<String, Integer> wordIndex = new HashMap<>();
        int n = words.length;
        for (int i = 0; i < n; i++) {
            wordIndex.put(new StringBuilder(words[i]).reverse().toString(), i);
        }

        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int k = words[i].length();
            if (k == 0) {
                continue;
            }

            for (int j = 0; j < k; j++) {
                if (isPalindrome(words[i], 0, j)) {
                    int index = findIndex(words[i], j + 1, k - 1, wordIndex);
                    if (index != -1 && index != i) {
                        ans.add(Arrays.asList(index, i));
                    }
                }
            }

            // 注意边界，这里 j 从 k 开始，不是 k-1
            for (int j = k; j >= 0; j--) {
                if (isPalindrome(words[i], j, k - 1)) {
                    int index = findIndex(words[i], 0, j - 1, wordIndex);
                    if (index != -1 && index != i) {
                        ans.add(Arrays.asList(i, index));
                    }
                }
            }
        }
        return ans;
    }

    private static int findIndex(String word, int l, int r, Map<String, Integer> wordIndex) {
        return wordIndex.getOrDefault(word.substring(l, r + 1), -1);
    }

    private static boolean isPalindrome(String word, int l, int r) {
        while (l < r) {
            if (word.charAt(l++) != word.charAt(r--)) {
                return false;
            }
        }
        return true;
    }

    // Manacher 优化判断前缀串是否为回文串
    static List<List<Integer>> palindromePairs2(String[] words) {
        Map<String, Integer> wordset = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            wordset.put(words[i], i);
        }
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            res.addAll(findAll(words[i], i, wordset));
        }
        return res;
    }

    private static List<List<Integer>> findAll(String word, int index, Map<String, Integer> words) {
        List<List<Integer>> res = new ArrayList<>();
        String reverse = reverse(word);

        Integer rest = words.get("");
        if (rest != null && rest != index && word.equals(reverse)) {
            addRecord(res, rest, index);
            addRecord(res, index, rest);
        }

        // manachers 字符串回文半径数组
        int[] rs = manachers(word);
        int mid = rs.length >> 1;
        for (int i = 1; i < mid; i++) {
            if (i - rs[i] == -1) {
                rest = words.get(reverse.substring(0, mid - i));
                if (rest != null && rest != index) {
                    addRecord(res, rest, index);
                }
            }
        }
        for (int i = mid + 1; i < rs.length; i++) {
            if (i + rs[i] == rs.length) {
                rest = words.get(reverse.substring((mid << 1) - i));
                if (rest != null && rest != index) {
                    addRecord(res, index, rest);
                }
            }
        }
        return res;
    }

    public static void addRecord(List<List<Integer>> res, int left, int right) {
        List<Integer> newr = new ArrayList<>();
        newr.add(left);
        newr.add(right);
        res.add(newr);
    }

    private static int[] manachers(String word) {
        char[] mchs = manachercs(word);
        int[] rs = new int[mchs.length];
        int center = -1;
        int pr = -1;
        for (int i = 0; i != mchs.length; i++) {
            rs[i] = pr > i ? Math.min(rs[(center << 1) - i], pr - i) : 1;
            while (i + rs[i] < mchs.length && i - rs[i] > -1) {
                if (mchs[i + rs[i]] != mchs[i - rs[i]]) {
                    break;
                }
                rs[i]++;
            }
            if (i + rs[i] > pr) {
                pr = i + rs[i];
                center = i;
            }
        }
        return rs;
    }

    private static char[] manachercs(String word) {
        char[] chs = word.toCharArray();
        char[] mchs = new char[chs.length * 2 + 1];
        int index = 0;
        for (int i = 0; i != mchs.length; i++) {
            mchs[i] = (i & 1) == 0 ? '#' : chs[index++];
        }
        return mchs;
    }

    private static String reverse(String str) {
        char[] chs = str.toCharArray();
        int l = 0;
        int r = chs.length - 1;
        while (l < r) {
            char tmp = chs[l];
            chs[l++] = chs[r];
            chs[r--] = tmp;
        }
        return String.valueOf(chs);
    }


    public static void main(String[] args) {

        String[] arr = {"abcd", "dcba", "lls", "s", "sssll"};
        List<List<Integer>> ans = palindromePairs(arr);
        List<List<Integer>> ans2 = palindromePairs2(arr);
        System.out.println(ans);
        System.out.println(ans2);

        String str = "abac";
        // #a#b#a#c#
        int[] pr = manachers(str);
        ArrayComparator.printArray(pr);
    }
}