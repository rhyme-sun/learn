package learn.algorithm.kmp;

import java.util.ArrayList;

import learn.algorithm.comparator.BinaryTreeComparator;
import learn.algorithm.structure.tree.Node;

/**
 * 判断一个树是否是另一棵树的子树
 */
public class SubTreeEqual {

    /**
     * 递归方法
     */
    static boolean containsTree1(Node big, Node small) {
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }
        if (isSameValueStructure(big, small)) {
            return true;
        }
        return containsTree1(big.left, small) || containsTree1(big.right, small);
    }

    /**
     * 判断两棵树结构是否相同
     */
    private static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    /**
     * KMP 方法
     */
    static boolean containsTree2(Node big, Node small) {
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }
        ArrayList<String> b = preSerial(big);
        ArrayList<String> s = preSerial(small);
        String[] str = new String[b.size()];
        for (int i = 0; i < str.length; i++) {
            str[i] = b.get(i);
        }

        String[] match = new String[s.size()];
        for (int i = 0; i < match.length; i++) {
            match[i] = s.get(i);
        }
        return getIndexOf(str, match) != -1;
    }

    /**
     * 先序遍历序列话，子节点为 null 补 null
     */
    private static ArrayList<String> preSerial(Node head) {
        ArrayList<String> ans = new ArrayList<>();
        pres(head, ans);
        return ans;
    }

    private static void pres(Node head, ArrayList<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    private static int getIndexOf(String[] str1, String[] str2) {
        if (str1 == null || str2 == null || str1.length < 1 || str1.length < str2.length) {
            return -1;
        }
        int x = 0;
        int y = 0;
        int[] next = getNextArray(str2);
        while (x < str1.length && y < str2.length) {
            if (isEqual(str1[x], str2[y])) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }

    private static int[] getNextArray(String[] ms) {
        if (ms.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < next.length) {
            if (isEqual(ms[i - 1], ms[cn])) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    private static boolean isEqual(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else {
            if (a == null || b == null) {
                return false;
            } else {
                return a.equals(b);
            }
        }
    }

    public static void main(String[] args) {
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Node big = BinaryTreeComparator.generateRandomBinaryTree(bigTreeLevel, nodeMaxValue);
            Node small = BinaryTreeComparator.generateRandomBinaryTree(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containsTree1(big, small);
            boolean ans2 = containsTree2(big, small);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("Finish!");
    }
}
