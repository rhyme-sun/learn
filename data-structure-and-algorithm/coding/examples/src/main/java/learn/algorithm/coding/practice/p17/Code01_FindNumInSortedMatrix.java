package learn.algorithm.coding.practice.p17;

/**
 * 编写一个高效的算法来搜索 `m*n` 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
 * <p>
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 */
public class Code01_FindNumInSortedMatrix {

    static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null) {
            return false;
        }
        int n = matrix.length;
        int m = matrix[0].length;

        // 左下角
        int i = n - 1;
        int j = 0;
        while (i >= 0 && j < m) {
            int cur = matrix[i][j];
            if (cur == target) {
                return true;
            } else if (cur > target) {
                i--;
            } else {
                j++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{0, 1, 2, 3, 4, 5, 6}, // 0
                {10, 12, 13, 15, 16, 17, 18}, // 1
                {23, 24, 25, 26, 27, 28, 29}, // 2
                {44, 45, 46, 47, 48, 49, 50}, // 3
                {65, 66, 67, 68, 69, 70, 71}, // 4
                {96, 97, 98, 99, 100, 111, 122}, // 5
                {166, 176, 186, 187, 190, 195, 200}, // 6
                {233, 243, 321, 341, 356, 370, 380} // 7
        };
        int K = 233;
        System.out.println(searchMatrix(matrix, K));
    }
}
