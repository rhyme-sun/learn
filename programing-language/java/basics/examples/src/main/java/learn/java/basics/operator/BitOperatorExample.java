package learn.java.basics.operator;

import lombok.extern.slf4j.Slf4j;

/**
 * 位运算操作符，BitOperator.
 */
@Slf4j
public class BitOperatorExample {

    public static void main(String[] args) {
        // -1(int) 的补码：  11111111111111111111111111111111
        // -2(int) 的补码：  11111111111111111111111111111110

        // -1(int) 按位取反：            00000000000000000000000000000000
        // -1(int) 和 -2(int) 按位与：   11111111111111111111111111111110
        // -1(int) 和 -2(int) 按位或：   11111111111111111111111111111111
        // -1(int) 和 -2(int) 按位异或或：00000000000000000000000000000001

        log.info("-1(int) 按位取反：{}", Integer.toBinaryString(~-1));
        log.info("-1(int) 和 -2(int) 按位与：{}", Integer.toBinaryString(-1 & -2));
        log.info("-1(int) 和 -2(int) 按位或：{}", Integer.toBinaryString(-1 | -2));
        log.info("-1(int) 和 -2(int) 按位异或：{}", Integer.toBinaryString(-1 ^ -2));
    }
}
