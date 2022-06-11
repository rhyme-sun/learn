package learn.algorithm.morris;

import learn.algorithm.structure.tree.Node;

/**
 * Morris 遍历二叉树的过程如下：
 * 记当前节点为 cur，
 * <p>
 * 如果 cur 无左孩子，cur 向右移动（cur=cur.right）；
 * 如果 cur 有左孩子，找到 cur 左子树上最右的节点，记为 mostRight；
 * - 如果 mostRight 的右指针指向空，让其指向 cur（mostRight.right = cur），然后 cur 向左移动（cur = cur.left）；
 * - 如果 mostRight 的右指针指向 cur，让其指向 null（mostRight.right = null），然后 cur 向右移动（cur = cur.right）；
 * cur 为空时遍历停止。
 */
public class Morris {

    /**
     * Morrios 遍历
     * 含有左子树的节点会遍历两次，不含左子树的节点会遍历一次
     */
    static void morris(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.value + " ");
            Node curL = cur.left;
            if (curL == null) {
                cur = cur.right;
            } else {
                // 找到左子树的最右节点
                Node mostRight = curL;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { // 第一次遍历到 cur
                    mostRight.right = cur;
                    cur = cur.left;
                } else { // 第二次遍历到 cur
                    mostRight.right = null;
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    /**
     * Morris 先序遍历（中左右）
     * 对于没有左子树的节点第一次遍历就打印，对于有左子树的节点第一次找最右节点时就打印，第二次不打印
     */
    static void morrisPre(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        while (cur != null) {
            Node curL = cur.left;
            if (curL == null) {
                System.out.print(cur.value + " ");
                cur = cur.right;
            } else {
                // 找到左子树的最右节点
                Node mostRight = curL;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    System.out.print(cur.value + " ");
                    mostRight.right = cur;
                    cur = cur.left;
                } else {
                    mostRight.right = null;
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    /**
     * 中序遍历（左中右）：
     * 对于没有左子树的节点第一次遍历就打印，对于有左子树的节点第一次不打印，第二次打印
     */
    static void morrisIn(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        while (cur != null) {
            Node curL = cur.left;
            if (curL == null) {
                System.out.print(cur.value + " ");
                cur = cur.right;
            } else {
                // 找到左子树的最右节点
                Node mostRight = curL;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                } else {
                    System.out.print(cur.value + " ");
                    mostRight.right = null;
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    /**
     * 后序遍历（左右中）：
     * 只处理有左子树的第二次访问，此时逆序打印左子树的右边界，
     * 最后逆序打印整棵树的右边界
     */
    static void morrisPos(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        while (cur != null) {
            Node curL = cur.left;
            if (curL == null) {
                cur = cur.right;
            } else {
                // 找到左子树的最右节点
                Node mostRight = curL;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                } else {
                    mostRight.right = null;
                    reversePrintLeftEdge(cur.left);
                    cur = cur.right;
                }
            }
        }
        reversePrintLeftEdge(head);
        System.out.println();
    }

    private static void reversePrintLeftEdge(Node from) {
        Node reverse = reverseEdge(from);
        Node cur = reverse;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        reverseEdge(reverse);
    }

    /**
     * 逆序树的右边界（单链表的翻转）
     *
     * @param from 起点节点
     */
    private static Node reverseEdge(Node from) {
        Node pre = null;
        Node cur = from;
        while (cur != null) {
            Node next = cur.right;
            cur.right = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        System.out.println("Morris 遍历：");
        morris(head);
        System.out.println("先序遍历：");
        morrisPre(head);
        System.out.println("中序遍历：");
        morrisIn(head);
        System.out.println("后序遍历：");
        morrisPos(head);
    }
}
