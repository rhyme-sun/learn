package learn.algorithm.basics.structure.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * 前缀树（Trie）
 */
public class Trie {

    /**
     * for test，使用 HashMap 实现类似于前缀树的功能
     */
    static class Right {

        /**
         * key 为字符串，value 为字符串出现个数
         */
        private final HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            return box.getOrDefault(word, 0);
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (Map.Entry<String, Integer> entry : box.entrySet()) {
                if (entry.getKey().startsWith(pre)) {
                    count += entry.getValue();
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (String s : arr) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(s);
                    trie2.insert(s);
                    right.insert(s);
                } else if (decide < 0.5) {
                    trie1.delete(s);
                    trie2.delete(s);
                    right.delete(s);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(s);
                    int ans2 = trie2.search(s);
                    int ans3 = right.search(s);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(s);
                    int ans2 = trie2.prefixNumber(s);
                    int ans3 = right.prefixNumber(s);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("Finish!");
    }
}

class Trie1 {

    private final Node root;

    public Trie1() {
        this.root = new Node();
    }

    public void insert(String word) {
        if (word == null) {
            return;
        }
        root.pass++;

        final char[] chars = word.toCharArray();
        Node node = root;
        for (char aChar : chars) {
            int index = aChar - 'a';
            if (node.nextNodes[index] == null) {
                node.nextNodes[index] = new Node();
            }
            node = node.nextNodes[index];
            node.pass++;
        }
        node.end++;
    }

    public void delete(String word) {
        if (search(word) != 0) {
            final char[] chars = word.toCharArray();
            Node node = root;
            for (char aChar : chars) {
                int index = aChar - 'a';
                if (--node.nextNodes[index].pass == 0) {
                    node.nextNodes[index] = null;
                    return;
                }
                node = node.nextNodes[index];
            }
            node.end--;
        }
    }

    public int search(String word) {
        if (word == null) {
            return 0;
        }
        final char[] chars = word.toCharArray();
        Node node = root;
        for (char aChar : chars) {
            int index = aChar - 'a';
            Node next = node.nextNodes[index];
            if (next == null) {
                return 0;
            }
            node = next;
        }
        return node.end;
    }

    public int prefixNumber(String pre) {
        if (pre == null) {
            return 0;
        }
        final char[] chars = pre.toCharArray();
        Node node = root;
        for (char aChar : chars) {
            int index = aChar - 'a';
            Node next = node.nextNodes[index];
            if (next == null) {
                return 0;
            }
            node = next;
        }
        return node.pass;
    }

    /**
     * 前缀树节点
     */
    static class Node {

        private int pass;
        private int end;
        private final Node[] nextNodes;

        public Node() {
            this.pass = 0;
            this.end = 0;
            // 26 表示 26 个小写字母，这里假设存放的字符串只由 26 个小写字母组成
            // 0 代表路径 a
            // 1 代表路径 b
            // ......
            // 25 代表路径 z
            this.nextNodes = new Node[26];
        }
    }
}

class Trie2 {

    private final Node root;

    public Trie2() {
        this.root = new Node();
    }

    public void insert(String word) {
        if (word == null) {
            return;
        }
        root.pass++;

        final char[] chars = word.toCharArray();
        Node node = root;
        for (char aChar : chars) {
            int index = aChar - 'a';
            node = node.nextNodes.computeIfAbsent(index, k -> new Node());
            node.pass++;
        }
        node.end++;
    }

    public void delete(String word) {
        if (search(word) != 0) {
            final char[] chars = word.toCharArray();
            Node node = root;
            for (char aChar : chars) {
                int index = aChar - 'a';
                if (--node.nextNodes.get(index).pass == 0) {
                    node.nextNodes.remove(index);
                    return;
                }
                node = node.nextNodes.get(index);
            }
            node.end--;
        }
    }

    public int search(String word) {
        if (word == null) {
            return 0;
        }
        final char[] chars = word.toCharArray();
        Node node = root;
        for (char aChar : chars) {
            int index = aChar - 'a';
            final Node next = node.nextNodes.get(index);
            if (next == null) {
                return 0;
            }
            node = next;
        }
        return node.end;
    }

    public int prefixNumber(String pre) {
        if (pre == null) {
            return 0;
        }
        final char[] chars = pre.toCharArray();
        Node node = root;
        for (char aChar : chars) {
            int index = aChar - 'a';
            final Node next = node.nextNodes.get(index);
            if (next == null) {
                return 0;
            }
            node = next;
        }
        return node.pass;
    }

    /**
     * 前缀树节点
     */
    static class Node {

        private int pass;
        private int end;
        private final Map<Integer, Node> nextNodes;

        public Node() {
            this.pass = 0;
            this.end = 0;
            // key 表示路径，value 表示路径指向的下个节点
            this.nextNodes = new HashMap<>();
        }
    }
}