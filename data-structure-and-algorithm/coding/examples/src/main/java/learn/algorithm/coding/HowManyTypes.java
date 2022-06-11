package learn.algorithm.coding;

import java.util.HashSet;
import java.util.Set;

import learn.algorithm.coding.comparator.StringComparator;

/**
 * 题目如下所示：
 * 只由小写字母（a~z）组成的一批字符串，都放在字符类型的数组 `String[] arr` 中，
 * 如果其中某两个字符串所含有的字符种类完全一样。就将两个字符串算作一类，比如："baacbba" 和 "bac" 就算作一类。返回 arr 中有多少类？
 */
public class HowManyTypes {

    static int howManyTypes(String[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Set<Integer> set = new HashSet<>();
        for (String s : arr) {
            set.add(charExistBitMap(s));
        }
        return set.size();
    }

    private static int charExistBitMap(String str) {
        int k = 0;
        for (char c : str.toCharArray()) {
            k |= 1 << c - 'a';
        }
        return k;
    }

    public static void main(String[] args) {
        testOnce();
    }

    private static void testOnce() {
        String[] strArr = {"aab", "ab", "aac", "b", "d"};
        int ans = howManyTypes(strArr);
        System.out.println(ans);
    }
}
