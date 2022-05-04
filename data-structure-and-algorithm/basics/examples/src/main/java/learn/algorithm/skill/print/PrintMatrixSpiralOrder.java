package learn.algorithm.skill.print;

import learn.algorithm.comparator.MatrixComparator;

/**
 * 给定一个长方形矩阵 matrix，实现顺时针转圈打印
 */
public class PrintMatrixSpiralOrder {

    static void spiralOrderPrint(int[][] matrix) {
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a <= c && b <= d) {
            printEdge(matrix, a++, b++, c--, d--);
        }
    }

    /**
     * 顺时针打印
     * b d
     * a
     * c
     */
    private static void printEdge(int[][] m, int a, int b, int c, int d) {
        int colTimes = d - b;
        int rowTimes = c - a;
        for (int col = 0; col < colTimes; col++) {
            System.out.print(m[a][b + col] + " ");
        }
        for (int row = 0; row < rowTimes; row++) {
            System.out.print(m[a + row][d] + " ");
        }
        for (int col = 0; col < colTimes; col++) {
            System.out.print(m[c][d - col] + " ");
        }
        for (int row = 0; row < rowTimes; row++) {
            System.out.print(m[c - row][b] + " ");
        }
    }


    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12},
                {13, 14, 15, 16}};
        MatrixComparator.printMatrix(matrix);
        System.out.println();
        spiralOrderPrint(matrix);
    }
}
