package learn.algorithm.coding.skill.tree;

/**
 * 给定一棵搜索二叉树头节点，转化成首尾相接的有序双向链表。
 * 顺序要求为中序遍历。
 * <p>
 * https://leetcode.cn/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
 */
public class Code06_BSTtoDoubleLinkedList {

    // 记录双链表的头节点
    static TreeNode head;
    // 记录双链表的上个节点
    static TreeNode p;

    // 方法一，使用遍历思路
    static TreeNode treeToDoublyList1(TreeNode root) {
        if (root == null) {
            return null;
        }
        traverse(root);
        head.left = p;
        p.right = head;
        return head;
    }

    private static void traverse(TreeNode x) {
        if (x == null) {
            return;
        }
        traverse(x.left);
        TreeNode node = new TreeNode(x.val);
        if (p == null) {
            head = node;
        } else {
            p.right = node;
            node.left = p;
        }
        p = node;
        traverse(x.right);
    }

    // 方法二，使用分解思路
    static TreeNode treeToDoublyList2(TreeNode root) {
        if (root == null) {
            return null;
        }
        Info info = process(root);
        info.head.left = info.tail;
        info.tail.right = info.head;
        return info.head;
    }

    // 考虑某个节点的子树，返回转成双链表的头部和尾部
    private static Info process(TreeNode x) {
        if (x == null) {
            return new Info(null, null);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        if (leftInfo.tail != null) {
            leftInfo.tail.right = x;
        }
        x.left = leftInfo.tail;
        x.right = rightInfo.head;
        if (rightInfo.head != null) {
            rightInfo.head.left = x;
        }
        TreeNode head = leftInfo.head == null ? x : leftInfo.head;
        TreeNode tail = rightInfo.tail == null ? x : rightInfo.tail;
        return new Info(head, tail);
    }

    private static class Info {
        private TreeNode head;
        private TreeNode tail;

        Info(TreeNode h, TreeNode t) {
            head = h;
            tail = t;
        }
    }

    private static void print(TreeNode head) {
        TreeNode p = head;
        boolean first = true;
        while (p != head || first) {
            first = false;
            System.out.print(p.val + " ");
            p = p.right;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(3);
        root1.right.right = new TreeNode(4);

        TreeNode root2 = new TreeNode(2);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(3);
        root2.right.right = new TreeNode(4);

        TreeNode head1 = treeToDoublyList1(root1);
        TreeNode head2 = treeToDoublyList2(root2);
        print(head1);

        print(head2);
    }
}
