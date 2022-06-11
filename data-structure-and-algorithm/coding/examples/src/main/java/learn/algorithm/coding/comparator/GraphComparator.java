package learn.algorithm.coding.comparator;

/**
 * 图比较器
 */
public class GraphComparator {

    /**
     * 生成一个 N*N 的随机图（邻接矩阵表示）
     *
     * @param maxSize  图的宽度
     * @param maxValue 图中节点之间的距离的最大值（非负）
     * @return 随机图
     */
    public static int[][] generateGraph(int maxSize, int maxValue) {
        int len = (int) (Math.random() * maxSize) + 1;
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * maxValue) + 1;
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        return matrix;
    }
}
