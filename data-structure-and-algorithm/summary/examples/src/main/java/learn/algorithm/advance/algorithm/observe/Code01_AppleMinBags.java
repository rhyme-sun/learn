package learn.algorithm.advance.algorithm.observe;

/**
 * 对数器找规律
 * 题目描述如下：
 * 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。这两种类型的袋子为：
 *
 * - 能装下 6 个苹果的袋子；
 * - 能装下 8 个苹果的袋子；
 *
 * 小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满。给定一个正整数 N，
 * 返回至少使用多少袋子。如果 N 无法让使用的每个袋子必须装满，返回 -1。
 */
public class Code01_AppleMinBags {

    /**
     * 暴力解法
     */
    static int minBags(int apple) {
        if (apple < 0) {
            return -1;
        }
        int bag8 = (apple >> 3);
        int rest = apple - (bag8 << 3);
        while (bag8 >= 0) {
            if (rest % 6 == 0) {
                return bag8 + (rest / 6);
            } else {
                bag8--;
                rest += 8;
            }
        }
        return -1;
    }

    /**
     * 用结果反推的解法
     */
    static int minBagAwesome(int apple) {
        if ((apple & 1) != 0) { // 如果是奇数，返回-1
            return -1;
        }
        if (apple < 18) {
            return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1
                    : (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
        }
        return (apple - 18) / 8 + 3;
    }

    public static void main(String[] args) {
        for (int apple = 1; apple < 200; apple++) {
            System.out.println(apple + " : " + minBags(apple));
        }
    }
}
