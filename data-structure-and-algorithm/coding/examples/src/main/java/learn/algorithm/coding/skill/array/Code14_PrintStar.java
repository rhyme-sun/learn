package learn.algorithm.coding.skill.array;

/**
 * 给出任意的 N，按照如下形状打印星号。
 *
 * ```
 * * * * * *
 * o o o o *
 * o * * o *
 * o * o o *
 * o * * * *
 * ```
 */
public class Code14_PrintStar {

    static void printStar(int N) {
        int leftUp = 0;
        int rightDown = N - 1;
        char[][] m = new char[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                m[i][j] = ' ';
            }
        }
        while (leftUp <= rightDown) {
            set(m, leftUp, rightDown);
            leftUp += 2;
            rightDown -= 2;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void set(char[][] m, int leftUp, int rightDown) {
        for (int col = leftUp; col <= rightDown; col++) {
            m[leftUp][col] = '*';
            m[leftUp + 1][col] = 'o';
        }
        for (int row = leftUp + 1; row <= rightDown; row++) {
            m[row][rightDown] = '*';
            m[row][rightDown - 1] = 'o';
        }
        for (int col = rightDown - 1; col > leftUp; col--) {
            m[rightDown][col] = '*';
            m[rightDown - 1][col] = 'o';
        }
        for (int row = rightDown; row > leftUp + 1; row--) {
            m[row][leftUp + 1] = '*';
            m[row][leftUp] = 'o';
        }
    }

    public static void main(String[] args) {
        printStar(10);
    }
}
