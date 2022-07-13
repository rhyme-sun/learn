package learn.algorithm.coding.skill.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，
 * 同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据（反序列化）。
 * <p>
 * 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，
 * 你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
 * <p>
 * https://leetcode.cn/problems/serialize-and-deserialize-binary-tree
 */
public class Code11_SerializeAndDeserialize {

    // 先序
    public static class CodecByPre {
        static final String NULL = "#";
        static final String SEP = ",";
        private StringBuilder ans = new StringBuilder();

        public String serialize(TreeNode root) {
            traverse(root);
            return ans.toString();
        }

        private void traverse(TreeNode head) {
            if (head == null) {
                ans.append(NULL).append(SEP);
                return;
            }
            ans.append(head.val).append(SEP);
            traverse(head.left);
            traverse(head.right);
        }

        private int index;

        public TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) {
                return null;
            }
            String[] nodes = data.split(SEP);
            return traverse(nodes);
        }

        private TreeNode traverse(String[] nodes) {
            if (index == nodes.length) {
                return null;
            }
            String node = nodes[index++];
            if (NULL.equals(node)) {
                return null;
            }
            TreeNode head = new TreeNode(Integer.valueOf(node));
            head.left = traverse(nodes);
            head.right = traverse(nodes);
            return head;
        }
    }

    // 中序
    public static class CodecByIn {
        static final String NULL = "#";
        static final String SEP = ",";
        private StringBuilder ans = new StringBuilder();

        public String serialize(TreeNode root) {
            traverse(root);
            return ans.toString();
        }

        private void traverse(TreeNode head) {
            if (head == null) {
                ans.append(NULL).append(SEP);
                return;
            }
            traverse(head.left);
            ans.append(head.val).append(SEP);
            traverse(head.right);
        }

        // 注意：中序没有反序列化
        // 比如下面这两棵树：其中序遍历结果都为 1,2 存在歧义
        //   1        2
        //    \      /
        //     2    1
        public TreeNode deserialize(String data) {
            return null;
        }
    }

    // 后序
    public static class CodecByPost {
        static final String NULL = "#";
        static final String SEP = ",";
        private StringBuilder ans = new StringBuilder();

        public String serialize(TreeNode root) {
            traverse(root);
            return ans.toString();
        }

        private void traverse(TreeNode head) {
            if (head == null) {
                ans.append(NULL).append(SEP);
                return;
            }
            traverse(head.left);
            traverse(head.right);
            ans.append(head.val).append(SEP);
        }

        private int index;

        public TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) {
                return null;
            }
            String[] nodes = data.split(SEP);
            index = nodes.length - 1;
            return traverse(nodes);
        }

        private TreeNode traverse(String[] nodes) {
            if (index == nodes.length) {
                return null;
            }
            String node = nodes[index--];
            if (NULL.equals(node)) {
                return null;
            }
            TreeNode head = new TreeNode(Integer.valueOf(node));
            head.right = traverse(nodes);
            head.left = traverse(nodes);
            return head;
        }
    }

    // 层次
    public static class CodecByLevel {
        static final String NULL = "#";
        static final String SEP = ",";
        private StringBuilder ans = new StringBuilder();

        public String serialize(TreeNode root) {
            if (root == null) {
                return "";
            }
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode poll = queue.poll();
                    if (poll == null) {
                        ans.append(NULL).append(SEP);
                    } else {
                        ans.append(poll.val).append(SEP);
                        queue.add(poll.left);
                        queue.add(poll.right);
                    }
                }
            }
            return ans.toString();
        }

        public TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) {
                return null;
            }
            String[] nodes = data.split(SEP);
            int index = 0;
            TreeNode root = new TreeNode(Integer.valueOf(nodes[index++]));
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode poll = queue.poll();
                    String left = nodes[index++];
                    if (!NULL.equals(left)) {
                        poll.left = new TreeNode(Integer.valueOf(left));
                        queue.add(poll.left);
                    }
                    String right = nodes[index++];
                    if (!NULL.equals(right)) {
                        poll.right = new TreeNode(Integer.valueOf(right));
                        queue.add(poll.right);
                    }
                }
            }
            return root;
        }
    }

    public static void main(String[] args) {
        String data = "1,2,#,#,3";
        CodecByLevel codec = new CodecByLevel();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        String serialize = codec.serialize(root);
        System.out.println(serialize);
        TreeNode deserialize = codec.deserialize(serialize);
        System.out.println();
    }
}
