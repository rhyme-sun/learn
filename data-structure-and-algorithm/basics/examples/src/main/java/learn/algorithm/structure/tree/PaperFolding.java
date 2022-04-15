package learn.algorithm.structure.tree;

/**
 * 二叉树折纸问题（二叉树的中序遍历）
 */
public class PaperFolding {

    /**
     * 打印折痕
     *
     * @param n 对折次数
     */
    public static void printAllFolds(int n) {
        process(1, n, true);
    }

    /**
     * 当前来到的节点（来到的折痕），节点是虚拟的
     *
     * @param i    节点所在层（对折次数）
     * @param n    层的总数（对折总次数）
     * @param down 折痕方向，true 表示下折痕，false 表示上折痕
     */
    public static void process(int i, int n, boolean down) {
        if (i > n) {
            return;
        }
        process(i + 1, n, true);
        System.out.print(down ? "down " : "up ");
        process(i + 1, n, false);
    }

    public static void main(String[] args) {
        int n = 4;
        printAllFolds(n);
    }
}
