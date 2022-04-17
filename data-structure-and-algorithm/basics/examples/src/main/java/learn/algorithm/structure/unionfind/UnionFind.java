package learn.algorithm.structure.unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 并查集
 */
public class UnionFind {

    /**
     * 并查集节点
     *
     * @param <V> 节点类型
     */
    static class Node<V> {

        V value;

        public Node(V v) {
            value = v;
        }
    }

    /**
     * 并查集
     *
     * @param <V> 节点类型
     */
    static class UnionFindSet<V> {
        /**
         * 节点值和节点 Hash 表
         */
        private HashMap<V, Node<V>> nodes;
        /**
         * 节点和父节点 Hash 表
         */
        private HashMap<Node<V>, Node<V>> parents;
        /**
         * 代表节点和集合数量 Hash 表
         */
        private HashMap<Node<V>, Integer> sizeMap;

        public UnionFindSet(List<V> values) {
            this.nodes = new HashMap<>();
            this.parents = new HashMap<>();
            this.sizeMap = new HashMap<>();
            for (V cur : values) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                // 一开始认为每个样本都在单独的集合里
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        /**
         * 判断两个元素是否在同一个集合内
         *
         * @param a a
         * @param b b
         * @return true/false
         */
        public boolean isSameSet(V a, V b) {
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        /**
         * 把 a 和 b 各自所在集合的所有点合并成一个集合
         *
         * @param a a
         * @param b b
         */
        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        /**
         * 找到代表节点
         * 给你一个节点，请你往上到不能再往上，把代表返回。
         * 代表节点的代表节点说自己
         *
         * @param cur 当前节点
         * @return 代表节点
         */
        private Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            // 链表扁平化
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        /**
         * 并查集内集合的数量
         *
         * @return 集合的数量
         */
        public int sets() {
            return sizeMap.size();
        }
    }
}
