package learn.algorithm.coding.skill.tree;

/**
 * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
 * <p>
 * 展开后的单链表应该同样使用 `TreeNode` ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
 * 展开后的单链表应该与二叉树 先序遍历 顺序相同。
 * <p>
 * https://leetcode.cn/problems/flatten-binary-tree-to-linked-list
 */
public class Code05_FlattenBinaryTree {

    // 虚拟头节点，dummy.right 就是结果
    TreeNode dummy = new TreeNode(-1);
    // 用来构建链表的指针
    TreeNode p = dummy;

    // 方法 1，使用遍历，但不符合题目要求
    TreeNode flatten(TreeNode root) {
        return dummy.right;
    }

    void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        // 前序位置
        p.right = new TreeNode(root.val);
        p = p.right;

        traverse(root.left);
        traverse(root.right);
    }

    // 方法二，使用分解的思路
    void flatten2(TreeNode root) {
        process(root);
    }

    // 考虑某个节点为头节点的子树，返回展开链表的头节点和尾节点
    private Info process(TreeNode head) {
        if (head == null) {
            return new Info();
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        if (leftInfo.head != null) {
            head.right = leftInfo.head;
            leftInfo.tail.right = rightInfo.head;
        } else {
            head.right = rightInfo.head;
        }
        TreeNode h = head;
        h.left = null;

        TreeNode t = null;
        if (rightInfo.tail != null) {
            t = rightInfo.tail;
        } else {
            t = leftInfo.tail == null ? h : leftInfo.tail;
        }
        return new Info(h, t);
    }

    private static class Info {
        private TreeNode head;
        private TreeNode tail;

        Info() {
        }

        Info(TreeNode head, TreeNode tail) {
            this.head = head;
            this.tail = tail;
        }
    }
}
