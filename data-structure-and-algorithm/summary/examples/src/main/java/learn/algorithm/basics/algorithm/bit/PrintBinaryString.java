package learn.algorithm.basics.algorithm.bit;

/**
 * 打印整数二进制
 */
public class PrintBinaryString {

    static void printBinaryString(int num) {
        for (int move = 31; move >= 0; move--) {
            System.out.print((num >> move) & 1);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int num = 1;
        printBinaryString(num);
        System.out.println(Integer.toBinaryString(num));

        num = 2;
        printBinaryString(num);
        System.out.println(Integer.toBinaryString(num));

        num = -1;
        printBinaryString(num);
        System.out.println(Integer.toBinaryString(num));
    }
}
