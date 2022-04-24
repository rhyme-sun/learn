package learn.algorithm.matrix;

/**
 * 瓷砖问题：
 * 用 1*2 的瓷砖，把 N*2 的区域填满，返回铺瓷砖的方法数。
 *
 * @author Simon
 */
public class TileProblem {

    /**
     * 还剩 n 长度的区域没有放瓷砖，且 n 以以前的区域全部铺满了
     * @param n 代填充区域面积
     * @return 返回方法数
     */
    static int num(int n) {
        if (n == 0) {
            return 0;
        }
        // 只能竖着放
        if (n == 1) {
            return 1;
        }
        // 两个瓷砖竖着放，两个瓷砖横着放
        if (n == 2) {
            return 2;
        }
        // n 位置的瓷砖竖着放
        int p1 = num(n - 1);
        // n 位置的瓷砖横着放
        int p2 = num(n - 2);
        return p1 + p2;
    }

    public static void main(String[] args) {
        int i = 20;
        System.out.println(num(i));
    }
}
