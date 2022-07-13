package learn.algorithm.coding.skill.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
 * 初始状态下，所有 next 指针都被设置为 NULL。
 * <p>
 * https://leetcode.cn/problems/populating-next-right-pointers-in-each-node
 */
public class Code04_ConnectNext {

    // 方法一，使用遍历
    Node connect(Node root) {
        if (root == null) return null;
        // 遍历「三叉树」，连接相邻节点
        traverse(root.left, root.right);
        return root;
    }

    // 将二叉树相邻节点看作一个节点，那么可以抽象成一颗三叉树。
    private void traverse(Node node1, Node node2) {
        if (node1 == null || node2 == null) {
            return;
        }
        // 将传入的两个节点穿起来
        node1.next = node2;

        // 连接相同父节点的两个子节点
        traverse(node1.left, node1.right);
        traverse(node2.left, node2.right);
        // 连接跨越父节点的两个子节点
        traverse(node1.right, node2.left);
    }


    // 方法二，同样使用先序遍历，但不用抽象成三叉树
    Node connect2(Node root) {
        traverse(root);
        return root;
    }

    private void traverse(Node head) {
        if (head == null) {
            return;
        }
        if (head.left != null) {
            head.left.next = head.right;
            // 头节点的 next 指针已经维护好
            // 头节点 next 指针不为 null 时，头节点的右孩子指向头节点 next 的左孩子
            head.right.next = head.next != null ? head.next.left : null;
            traverse(head.left);
            traverse(head.right);
        }
    }

    // 方法三，层次遍历
    Node connect3(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Node pre = null;
            for (int i = 0; i < size; i++) {
                Node poll = queue.poll();
                if (pre != null) {
                    pre.next = poll;
                }
                pre = poll;
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
        }
        return root;
    }

    private static class Node {
        Node left;
        Node right;
        Node next;
    }
}
