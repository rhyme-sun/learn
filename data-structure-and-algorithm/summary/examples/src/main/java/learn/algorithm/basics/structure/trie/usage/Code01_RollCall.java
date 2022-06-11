package learn.algorithm.basics.structure.trie.usage;

import java.util.Scanner;

/**
 * 给你 n 个名字串，然后进行 m 次点名，每次你需要回答“名字不存在”、“第一次点到这个名字”、“已经点过这个名字”之一。
 * `1<n<10^4`，`1<m<10^5`，所有字符串长度不超过 50。
 */
public class Code01_RollCall {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] names = new String[n];
        for (int i = 0; i < n; i++) {
            names[i] = sc.next();
        }
        int m = sc.nextInt();
        String[] calls = new String[m];
        for (int i = 0; i < m; i++) {
            calls[i] = sc.next();
        }
        String[] ans = rollCall(names, calls);
        for (String s : ans) {
            System.out.println(s);
        }
        sc.close();
    }

    private static final String OK = "OK", WRONG = "WRONG", REPEAT = "REPEAT";

    static String[] rollCall(String[] names, String[] calls) {
        if (names == null || names.length == 0 || calls == null) {
            return null;
        }
        Trie trie = new Trie();
        for (String name : names) {
            trie.insert(name);
        }

        int n = calls.length;

        String[] ans = new String[n];
        for (int i = 0; i < n; i++) {
            ans[i] = trie.call(calls[i]);
        }
        return ans;
    }

    static class Trie {
        Node root;
        Trie() {
            root = new Node();
        }

        public void insert(String name) {
            if (name == null || name.length() == 0) {
                return;
            }
            Node cur = root;
            for (int i = 0; i < name.length(); i++) {
                int path = name.charAt(i) - 'a';
                if (cur.next[path] == null) {
                    cur.next[path] = new Node();
                }
                cur = cur.next[path];
            }
        }

        public String call(String name) {
            if (name == null || name.length() == 0) {
                return WRONG;
            }
            Node cur = root;
            for (int i = 0; i < name.length(); i++) {
                int path = name.charAt(i) - 'a';
                if (cur.next[path] == null) {
                    return WRONG;
                }
                cur = cur.next[path];
            }
            if (cur.hasCall) {
                return REPEAT;
            } else {
                cur.hasCall = true;
                return OK;
            }
        }

        static class Node {
            boolean hasCall;
            Node[] next;
            Node() {
                next = new Node[26];
            }
        }
    }
}
