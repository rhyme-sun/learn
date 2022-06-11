package learn.algorithm.practice.p01;

/**
 * 题目描述如下：
 * 给定一个非负整数 num，如何不用循环语句，返回大于等于 num，并且离 num 最近的 2 的某次方。
 */
public class Code03_Near2Power {

    static final int tableSizeFor2(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : n + 1;
    }

    static int tableSizeFor(int n) {
        // 减 1 是为了应对 n 为 2 的幂的情况
        n--;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    public static void main(String[] args) {
        // 二进制右侧连续的 0 的个数
        System.out.println(Integer.numberOfLeadingZeros(0));
        System.out.println(Integer.numberOfLeadingZeros(1));
    }
}
