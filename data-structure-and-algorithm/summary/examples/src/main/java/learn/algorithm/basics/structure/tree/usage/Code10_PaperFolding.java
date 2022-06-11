package learn.algorithm.basics.structure.tree.usage;

/**
 * 二叉树折纸问题，题目描述如下：
 *
 * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折 1 次，压出折痕后展开。
 * 此时折痕是凹下去的，即折痕突起的方向指向纸条的背面，称这样的折痕为下折痕，否则为上折痕。
 * 如果从纸条的下边向上方连续对折 2 次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
 * 给定一个输入参数 n，代表纸条都从下边向上方连续对折 n 次，请从上到下打印所有折痕的方向。
 * 例如，n=1 时，打印 down；n=2 时，打印 down down up。
 */
public class Code10_PaperFolding {

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
        int n = 3;
        printAllFolds(n);
    }
}
