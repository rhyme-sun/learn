package learn.algorithm.advance.structure.ac;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AC {

    /**
     * 前缀树节点
     */
    static class Node {
        /**
         * 表示节点是否为前缀树上的结尾节点
         * 如果不为 null，表示这个节点是某个字符串的结尾，且 end 的值就是这个字符串，如果 end 为 null，则表示这个节点不是结尾节点
         */
        public String end;
        /**
         * 表示结尾节点是否已经使用过，在匹配铭感词时可以避免重复匹配
         */
        public boolean endUse;
        public Node fail;
        public Node[] nexts;

        public Node() {
            endUse = false;
            end = null;
            fail = null;
            nexts = new Node[26];
        }
    }

    static class ACAutomation {

        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        /**
         * 构建前缀树
         */
        public void insert(String s) {
            char[] str = s.toCharArray();
            Node cur = root;
            int index;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];
            }
            cur.end = s;
        }

        /**
         * 维护好 fail 指针，使用宽度优先遍历的方式去遍历
         */
        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                // cur 为父节点，依次考察其下的每个子节点，并维护好 fail 指针
                Node cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if (cur.nexts[i] != null) {
                        cur.nexts[i].fail = root;
                        Node cfail = cur.fail;
                        while (cfail != null) {
                            if (cfail.nexts[i] != null) {
                                cur.nexts[i].fail = cfail.nexts[i];
                                break;
                            }
                            cfail = cfail.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        /**
         * 匹配敏感词
         *
         * @param content 文章内容
         * @return 文章中包含的铭感词列表
         */
        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            int index;
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                // 如果当前字符在这条路上没配出来，就随着 fail 方向走向下条路径
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                // 1) 现在来到的路径，是可以继续匹配的
                // 2) 现在来到的节点，就是前缀树的根节点
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;

                Node follow = cur;
                while (follow != root) {
                    if (follow.endUse) {
                        break;
                    }
                    // 不同的需求，在这一段之间修改
                    if (follow.end != null) {
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    // 不同的需求，在这一段之间修改
                    follow = follow.fail;
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        // 设置fail指针
        ac.build();

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
        for (String word : contains) {
            System.out.println(word);
        }
    }

}
