package learn.algorithm.basics.structure.tree.dp;

import java.util.LinkedList;
import java.util.Queue;

import learn.algorithm.basics.structure.tree.Node;
import learn.algorithm.comparator.BinaryTreeComparator;

/**
 * 判断二叉树是否为满二叉树
 * 如果一棵树的高度为 h，那么该树一定有 2^h-1 个节点，满足这样性质的树就叫做满二叉树
 */
public class Code03_IsFBT {

    /**
     * 方法一，宽度优先遍历
     */
    static boolean isFull1(Node root) {
        if (root == null) {
            return true;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int size = 0;
        int level = 1;
        int levelSize = 1;
        boolean ans = true;
        while (!queue.isEmpty()) {
            while (levelSize-- > 0) {
                Node poll = queue.poll();
                size++;
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
            if ((1 << level) - 1 != size) {
                ans = false;
                break;
            }
            level++;
            levelSize = queue.size();
        }
        return ans;
    }

    /**
     * 方法二，递归套路
     */
    static boolean isFull2(Node root) {
        if (root == null) {
            return true;
        }
        return process(root).isFull;
    }

    private static Info process(Node head) {
        if (head == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = false;
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isFull = true;
        }
        return new Info(isFull, height);
    }

    private static class Info {
        boolean isFull;
        int height;

        Info(boolean isFull, int height) {
            this.isFull = isFull;
            this.height = height;
        }
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = BinaryTreeComparator.generateRandomBinaryTree(maxLevel, maxValue);
            if (isFull1(head) != isFull2(head)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finish!");
    }
}
