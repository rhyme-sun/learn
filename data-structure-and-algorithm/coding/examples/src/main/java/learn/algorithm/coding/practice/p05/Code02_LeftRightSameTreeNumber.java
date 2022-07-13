package learn.algorithm.coding.practice.p05;

import java.util.Objects;

/**
 * 题目描述如下：
 * <p>
 * 如果一个节点 X，它左树结构和右树结构完全一样，那么我们说以 X 为头的树是相等树。
 * 给定一棵二叉树的头节点 head，返回 head 整棵树上有多少棵相等子树。
 */
public class Code02_LeftRightSameTreeNumber {

    static int sameNumber(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head);
    }

    // 考的以 x 为父节点的字符的相等子树是多少
    private static int process(Node x) {
        if (x == null) {
            return 0;
        }
        int left = process(x.left);
        int right = process(x.right);
        return left + right + (isSame(x.left, x.right) ? 1 : 0);
    }

    private static boolean isSame(Node left, Node right) {
        if (left == null ^ right == null) {
            return false;
        }
        if (left == null && right == null) {
            return true;
        }
        // 两个子树根节点值相等，左子树相等，右子树相等，则这两棵子树相等
        return left.value == right.value && isSame(left.left, right.left) && isSame(left.right, right.right);
    }

    /**
     * 使用先序列化的 Hash 判断子树是否相等
     */
    static int sameNumber2(Node head) {
        if (head == null) {
            return 0;
        }
        String algorithm = "SHA-256";
        Hash hash = new Hash(algorithm);
        return process(head, hash).number;
    }

    private static Info process(Node x, Hash hash) {
        if (x == null) {
            return new Info(0, "#,");
        }
        Info left = process(x.left, hash);
        Info right = process(x.right, hash);
        String h = hash.hashCode(x.value + "," + left.hash + "," + right.hash);
        int number = left.number + right.number + (Objects.equals(left.hash, right.hash) ? 1 : 0);
        return new Info(number, h);
    }

    private static class Info {
        int number;
        String hash;

        Info(int n, String h) {
            number = n;
            hash = h;
        }
    }

    // for test
	private static Node randomBinaryTree(int restLevel, int maxValue) {
		if (restLevel == 0) {
			return null;
		}
		Node head = Math.random() < 0.2 ? null : new Node((int) (Math.random() * maxValue));
		if (head != null) {
			head.left = randomBinaryTree(restLevel - 1, maxValue);
			head.right = randomBinaryTree(restLevel - 1, maxValue);
		}
		return head;
	}

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void main(String[] args) {
        int maxLevel = 8;
        int maxValue = 4;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            Node head = randomBinaryTree(maxLevel, maxValue);
            int ans1 = sameNumber(head);
            int ans2 = sameNumber2(head);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
