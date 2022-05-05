package learn.algorithm.huffman;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * 哈夫曼树
 */
public class HuffmanTree {

    /**
     * 哈夫曼树节点
     */
    static class Node {
        public int count;
        public Node left;
        public Node right;

        public Node(int c) {
            count = c;
        }
    }

    static class NodeComp implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.count - o2.count;
        }
    }

    /**
     * 生成词频表
     */
    private static HashMap<Character, Integer> countMap(String str) {
        HashMap<Character, Integer> ans = new HashMap<>();
        char[] s = str.toCharArray();
        for (char cha : s) {
            if (!ans.containsKey(cha)) {
                ans.put(cha, 1);
            } else {
                ans.put(cha, ans.get(cha) + 1);
            }
        }
        return ans;
    }

    /**
     * 根据哈夫曼编码表，将原始字符串转译成哈夫曼编码返回
     *
     * @param str         原始字符串
     * @param huffmanForm 哈夫曼编码表
     * @return 哈夫曼编码字符串
     */
    public static String huffmanEncode(String str, HashMap<Character, String> huffmanForm) {
        char[] s = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char cha : s) {
            builder.append(huffmanForm.get(cha));
        }
        return builder.toString();
    }

    /**
     * 根据词频表生成哈夫曼编码，这里使用 0 和 1 字符表示，没有转换成字节数组
     *
     * @param countMap 词频表
     * @return 字符对应的哈夫曼编码
     */
    private static HashMap<Character, String> huffmanForm(HashMap<Character, Integer> countMap) {
        HashMap<Character, String> ans = new HashMap<>();
        if (countMap.size() == 1) {
            for (char key : countMap.keySet()) {
                ans.put(key, "0");
            }
            return ans;
        }
        // 记录节点（都为叶子节点）对应的字符
        HashMap<Node, Character> nodes = new HashMap<>();
        PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComp());
        for (Entry<Character, Integer> entry : countMap.entrySet()) {
            Node cur = new Node(entry.getValue());
            char cha = entry.getKey();
            nodes.put(cur, cha);
            heap.add(cur);
        }
        while (heap.size() != 1) {
            Node a = heap.poll();
            Node b = heap.poll();
            Node h = new Node(a.count + b.count);
            h.left = a;
            h.right = b;
            heap.add(h);
        }
        Node head = heap.poll();
        fillForm(head, "", nodes, ans);
        return ans;
    }

    private static void fillForm(Node head, String pre, HashMap<Node, Character> nodes, HashMap<Character, String> ans) {
        if (nodes.containsKey(head)) { // 该节点为叶子节点，对应某个字符
            ans.put(nodes.get(head), pre);
        } else {
            fillForm(head.left, pre + "0", nodes, ans);
            fillForm(head.right, pre + "1", nodes, ans);
        }
    }

    /**
     * 根据哈夫曼编码表，还原成原始字符串
     */
    public static String huffmanDecode(String huffmanEncode, HashMap<Character, String> huffmanForm) {
        TrieNode root = createTrie(huffmanForm);
        TrieNode cur = root;
        char[] encode = huffmanEncode.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < encode.length; i++) {
            int index = encode[i] == '0' ? 0 : 1;
            cur = cur.nexts[index];
            if (cur.nexts[0] == null && cur.nexts[1] == null) {
                builder.append(cur.value);
                cur = root;
            }
        }
        return builder.toString();
    }

    /**
     * 生成哈夫曼树（可以看作是一个只有 0 1 字符的前缀树）
     */
    private static TrieNode createTrie(HashMap<Character, String> huffmanForm) {
        TrieNode root = new TrieNode();
        for (char key : huffmanForm.keySet()) {
            char[] path = huffmanForm.get(key).toCharArray();
            TrieNode cur = root;
            for (int i = 0; i < path.length; i++) {
                int index = path[i] == '0' ? 0 : 1;
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new TrieNode();
                }
                cur = cur.nexts[index];
            }
            cur.value = key;
        }
        return root;
    }

    static class TrieNode {
        public char value;
        public TrieNode[] nexts;

        public TrieNode() {
            value = 0;
            nexts = new TrieNode[2];
        }
    }

    // 为了测试
    public static String randomNumberString(int len, int range) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * range) + 'a');
        }
        return String.valueOf(str);
    }

    // 为了测试
    public static void main(String[] args) {
        // 根据词频表生成哈夫曼编码表
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('a', 3);
        map.put('b', 20);
        map.put('c', 30);
        map.put('d', 15);
        map.put('e', 50);
        HashMap<Character, String> huffmanForm = huffmanForm(map);
        for (Entry<Character, String> entry : huffmanForm.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println();
        // str是原始字符串
        String str = "CBBBAABBACAABDDEFBA";
        System.out.println(str);
        // countMap是根据str建立的词频表
        HashMap<Character, Integer> countMap = countMap(str);
        // hf是根据countMap生成的哈夫曼编码表
        HashMap<Character, String> hf = huffmanForm(countMap);
        // huffmanEncode是原始字符串转译后的哈夫曼编码
        String huffmanEncode = huffmanEncode(str, hf);
        System.out.println(huffmanEncode);
        // huffmanDecode是哈夫曼编码还原成的原始字符串
        String huffmanDecode = huffmanDecode(huffmanEncode, hf);
        System.out.println(huffmanDecode);
        System.out.println("");
        System.out.println("大样本随机测试开始");
        // 字符串最大长度
        int len = 500;
        // 所含字符种类
        int range = 26;
        // 随机测试进行的次数
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * len) + 1;
            String test = randomNumberString(N, range);
            HashMap<Character, Integer> counts = countMap(test);
            HashMap<Character, String> form = huffmanForm(counts);
            String encode = huffmanEncode(test, form);
            String decode = huffmanDecode(encode, form);
            if (!test.equals(decode)) {
                System.out.println(test);
                System.out.println(encode);
                System.out.println(decode);
                System.out.println("Oops!");
            }
        }
        System.out.println("Finish!");
    }
}
