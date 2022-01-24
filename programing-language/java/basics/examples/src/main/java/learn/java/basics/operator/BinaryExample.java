package learn.java.basics.operator;

import lombok.extern.slf4j.Slf4j;

/**
 * Java 中的整数（带符号）在内存中是以二进制格式存储的，二进制有三种格式：
 * 原码：一个带符号整数的符号位加上数值位的二进制表示叫做原码，其中整数的最高位符号位，为 0 表示正数，为 1 表示负数；
 * 反码：正数的反码和原码相同，负数的反码符号位不变，数值位按位取反；
 * 补码：正数的补码和原码相同，负数的补码位反码加 1。
 */
@Slf4j
public class BinaryExample {

    public static void main(String[] args) {
        // -1(int) 的原码：100000000000000000000000000000001
        // -1(int) 的反码：111111111111111111111111111111110
        // -1(int) 的补码：111111111111111111111111111111111

        log.info("-1(int) 的补码：{}", Integer.toBinaryString(-1));
    }
}
