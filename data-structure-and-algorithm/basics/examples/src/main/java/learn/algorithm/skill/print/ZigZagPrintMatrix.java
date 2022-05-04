package learn.algorithm.skill.print;

import learn.algorithm.comparator.MatrixComparator;

public class ZigZagPrintMatrix {

    static void printMatrixZigZag(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = 0;
        int dC = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean fromUp = false;
        while (tR != endR + 1) {
            printDiagonalLine(matrix, tR, tC, dR, dC, fromUp);
            tR = tC == endC ? tR + 1 : tR;
            tC = tC == endC ? tC : tC + 1;
            dC = dR == endR ? dC + 1 : dC;
            dR = dR == endR ? dR : dR + 1;
            fromUp = !fromUp;
        }
        System.out.println();
    }

    /**
     * 打印矩阵次对角线位置的元素，
     *
     * @param tR    右上横坐标
     * @param tC    右上纵坐标
     * @param dR    左下横坐标
     * @param dC    左下纵坐标
     * @param order 打印方向，true 表示从右上到左下打印，false 表示从左下到右上打印
     */
    private static void printDiagonalLine(int[][] m, int tR, int tC, int dR, int dC, boolean order) {
        if (order) {
            for (int i = 0; i <= dR - tR; i++) {
                System.out.print(m[tR + i][tC - i] + " ");
            }
        } else {
            for (int i = 0; i <= dR - tR; i++) {
                System.out.print(m[dR - i][dC + i] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        MatrixComparator.printMatrix(matrix);
        printMatrixZigZag(matrix);
    }
}
