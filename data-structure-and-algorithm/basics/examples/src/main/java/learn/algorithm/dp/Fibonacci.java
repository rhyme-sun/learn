package learn.algorithm.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 斐波那契数列，第 n 项的值等于前两项的和，比如：
 * 1,1,2,3,5,8,....
 */
public class Fibonacci {

    /**
     * 求斐波那契数列第 n 项的值
     */
    static int f(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 1;
        }
        return f(n - 1) + f(n - 2);
    }

    static int f(int n, Map<Integer, Integer> cache) {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        if (n == 1) {
            cache.put(1, 1);
            return 1;
        }
        if (n == 2) {
            cache.put(2, 1);
            return 1;
        }
        int ans = f(n - 1) + f(n - 2);
        cache.put(n, ans);
        return ans;
    }

    public static void main(String[] args) {
        final int f1 = f(6);
        System.out.println(f1);

        Map<Integer, Integer> cache = new HashMap<>();
        final int f2 = f(6, cache);
        System.out.println(f2);
    }
}
