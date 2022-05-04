package learn.algorithm.skill.result;

/**
 * 对数据找规律
 * 题目描述如下：
 * 给定一个正整数 N，表示有 N 份青草统一堆放在仓库里，有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草
 * 不管是牛还是羊，每一轮能吃的草量必须是：1，4，16，64...
 * 谁最先把草吃完，谁获胜，假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定，根据唯一的参数 N，返回谁会赢。
 */
public class EatGrass {

    /**
     * n 份草
     *
     * @param n n 份草
     * @return 先手赢返回先手，后手赢返回后手
     */
    static String whoWin(int n) {
        if (n < 5) {
            return n == 0 || n == 2 ? "后手" : "先手";
        }
        // 当前为先手，考虑是否能赢
        int want = 1;
        while (want <= n) {
            // 先手吃 want 的草能赢表示，后手吃 n-want 的草也能赢
            if (whoWin(n - want).equals("后手")) {
                return "先手";
            }
            // 避免越界，返回下一轮吃草数量
            if (want <= (n / 4)) {
                want *= 4;
            } else {
                break;
            }
        }
        return "后手";
    }

    public static String winner2(int n) {
        if (n % 5 == 0 || n % 5 == 2) {
            return "后手";
        } else {
            return "先手";
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 50; i++) {
            System.out.println(i + " : " + whoWin(i));
        }
    }
}
