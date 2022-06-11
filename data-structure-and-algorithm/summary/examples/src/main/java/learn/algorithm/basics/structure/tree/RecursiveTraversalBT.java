package learn.algorithm.basics.structure.tree;

/**
 * 二叉树的先中后遍历（基于递归实现）
 */
public class RecursiveTraversalBT {

    static void f(Node head) {
        if (head == null) {
            return;
        }
        // 1 第 1 次访问某个节点
        f(head.left);
        // 2 第 2 次访问某个节点
        f(head.right);
        // 3 第 3 次访问某个节点
    }

    /**
     * 先序遍历
     */
    static void pre(Node head) {
        if (head == null) {
            return;
        }
        System.out.println(head.value);
        pre(head.left);
        pre(head.right);
    }

    /**
     * 中序遍历
     */
    static void in(Node head) {
        if (head == null) {
            return;
        }
        in(head.left);
        System.out.println(head.value);
        in(head.right);
    }

    /**
     * 后序遍历
     */
    static void pos(Node head) {
        if (head == null) {
            return;
        }
        pos(head.left);
        pos(head.right);
        System.out.println(head.value);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        System.out.println("先序遍历");
        pre(head);
        System.out.println();
        System.out.println("中序遍历");
        in(head);
        System.out.println();
        System.out.println("后序遍历");
        pos(head);
        System.out.println();
    }
}
