package learn.algorithm.practice.p14;

/**
 * 给定一个棵完全二叉树，返回这棵树的节点个数，要求时间复杂度小于 O(N)。
 */
public class Code03_CompleteTreeNodeNumber {

    static int nodeNum(Node head) {
        if (head == null) {
            return 0;
        }
        int h = mostLeftLevel(head, 1);
        return process(head, 1, h);
    }

    // x 为根节点子树的节点数量
    // 子树为完全二叉树
    // level x 节点所在层次
    // h 整个树的高度
    private static int process(Node x, int level, int h) {
        if (level == h) {
            return 1;
        }
        // 右子树的最左侧节点是否触底
        if (mostLeftLevel(x.right, level + 1) == h) { // 触底
            int left = (1 << (h - level)) - 1;
            return left + 1 + process(x.right, level + 1, h);
        } else { // 没触底
            int right = (1 << (h - level - 1)) - 1;
            return process(x.left, level + 1, h) + 1 + right;
        }
    }

    // 求 x 最左侧节点层次
    // curLevel x 节点所在层次
    private static int mostLeftLevel(Node x, int curLevel) {
        Node cur = x;
        int level = curLevel;
        while (cur != null) {
            level++;
            cur = cur.left;
        }
        return level - 1;
    }

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        System.out.println(nodeNum(head));
        head.left = new Node(2);
        System.out.println(nodeNum(head));
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        System.out.println(nodeNum(head));
    }
}
