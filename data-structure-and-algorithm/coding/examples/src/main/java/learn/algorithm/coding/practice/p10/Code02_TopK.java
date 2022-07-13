package learn.algorithm.coding.practice.p10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * 在实时数据流中找到最常使用的 k 个单词。
 * <p>
 * 实现 `TopK` 类中的三个方法:
 * <p>
 * - `TopK(k)`,  构造方法；
 * - `add(word)`,  增加一个新单词；
 * - `topk()`,  得到当前最常使用的 k 个单词。
 */
public class Code02_TopK {

    static class TopK {
        private Node[] heap;
        // 堆中元素个数
        private int heapSize;
        // 词频表
        private Map<String, Node> nodeMap;
        // 反向索引表，词频节点在堆（数组）中的下标
        private Map<Node, Integer> indexMap;
        private Comparator<Node> comp;

        public TopK(int k) {
            heap = new Node[k < 0 ? 0 : k];
            nodeMap = new HashMap<>();
            indexMap = new HashMap<>();
            // 词频值小根堆，如果词频相同，字典序大的在前面
            comp = (o1, o2) -> o1.times == o2.times ? -o1.word.compareTo(o2.word) : (o1.times - o2.times);
        }

        public void add(String word) {
            if (heap.length == 0) {
                return;
            }
            int index = indexOf(word);
            if (index >= 0) {
                Node node = nodeMap.get(word);
                node.times++;
                // 调整堆
                heapify(index);
            } else {
                Node node = nodeMap.computeIfAbsent(word, key -> new Node(key, 0));
                node.times++;
                if (heapSize < heap.length) {
                    // 入堆
                    heap[heapSize] = node;
                    indexMap.put(node, heapSize);
                    heapInsert(heapSize++);
                } else {
                    if (comp.compare(heap[0], node) < 0) {
                        // 移除堆顶元素，再入堆
                        indexMap.put(heap[0], -1);
                        heap[0] = node;
                        indexMap.put(node, 0);
                        heapify(0);
                    }
                }
            }
        }

        private int indexOf(String word) {
            Node node = nodeMap.get(word);
            Integer index = indexMap.get(node);
            return index == null ? -1 : index;
        }

        private void heapInsert(int index) {
            while (comp.compare(heap[index], heap[(index - 1) / 2]) < 0) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                // 左右子节点小的那个，左右节点相同，选择右子节点
                int right = left + 1;
                int best = right < heapSize && comp.compare(heap[right], heap[left]) <= 0 ? right : left;
                best = comp.compare(heap[index], heap[best]) < 0 ? index : best;
                if (best == index) {
                    break;
                }
                swap(index, best);
                index = best;
                left = index * 2 + 1;
            }
        }

        private void swap(int i, int j) {
            Node tmp = heap[i];
            heap[i] = heap[j];
            heap[j] = tmp;
            indexMap.put(heap[i], i);
            indexMap.put(heap[j], j);
        }

        public List<String> topk() {
            // 堆只有依次弹出才能有序
            int len = heapSize;
            String[] ans = new String[len];
            Node[] copy = Arrays.copyOf(heap, len);
            while (len > 0) {
                ans[len - 1] = copy[0].word;
                swap(copy, 0, --len);
                heapify(copy, 0, len);
            }
            return Arrays.asList(ans);
        }

        private void heapify(Node[] heap, int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                // 左右子节点小的那个，左右节点相同，选择右子节点
                int right = left + 1;
                int best = right < heapSize && comp.compare(heap[right], heap[left]) <= 0 ? right : left;
                best = comp.compare(heap[index], heap[best]) < 0 ? index : best;
                if (best == index) {
                    break;
                }
                swap(heap, index, best);
                index = best;
                left = index * 2 + 1;
            }
        }

        private void swap(Node[] heap, int i, int j) {
            Node tmp = heap[i];
            heap[i] = heap[j];
            heap[j] = tmp;
        }

        static class Node {
            String word;
            int times;

            Node(String w, int t) {
                word = w;
                times = t;
            }
        }
    }

    static class TopK2 {
        private int k;
        private HashMap<String, Integer> counts;
        private TreeSet<String> topk;
        private Comparator<String> comp;

        public TopK2(int k) {
            this.k = k;

            counts = new HashMap<>();
            comp = Comparator.comparing((String w) -> -counts.get(w)).thenComparing(Comparator.naturalOrder());
            topk = new TreeSet<>(comp);
        }

        // O(logk)
        public void add(String word) {
            // 当红黑树里已经存在 word，必须先删除再添加，让该 word 按新的排位存储
            if (counts.containsKey(word) && topk.contains(word)) {
                topk.remove(word);
            }
            counts.merge(word, 1, Integer::sum);
            topk.add(word);
            if (topk.size() > k) {
                topk.pollLast();
            }
        }

        // O(k*logk)
        public List<String> topk() {
            List<String> ans = new ArrayList<>();
            for (String str : topk) {
                ans.add(str);
            }
            return ans;
        }
    }

    public class TopK3 {
        private int k;
        private HashMap<String, Integer> counts;
        private PriorityQueue<String> topk;
        private Comparator<String> comp;

        public TopK3(int k) {
            this.k = k;
            counts = new HashMap<>();
            comp = Comparator.comparing((String w) -> counts.get(w)).thenComparing(Comparator.reverseOrder());
            topk = new PriorityQueue<>(comp);
        }

        // O(k): PQ 是 O(k) contains 查询，PQ 的 remove 是 O(k) 查询 + O(logk) poll
        public void add(String word) {
            // 当堆里已经存在该 word 了，必须先删除再添加，让该 word 按新的排位存储
            if (topk.contains(word)) {
                topk.remove(word);
            }
            counts.merge(word, 1, Integer::sum);
            topk.offer(word);
            if (topk.size() > k) {
                topk.poll();
            }
        }

        // O(k*logk)
        public List<String> topk() {
            List<String> result = Arrays.asList(topk.toArray(new String[0]));
            Collections.sort(result, comp.reversed());
            return result;

        /*String[] result = new String[topk.size()];
        for (int i = topk.size() - 1; i >= 0; i--) {
            result[i] = topk.poll();
        }
        // PQ 只能 poll 出数据才能得到有序排列，最后还必须再把数据 offer 回 PQ 以恢复这些数据
        for (String word : result) {
            topk.offer(word);
        }
        return Arrays.asList(result);*/
        }
    }

    public static void main(String[] args) {
        TopK topK = new TopK(3);
        topK.add("ba");
        System.out.println(topK.topk());
        topK.add("fe");
        System.out.println(topK.topk());
        topK.add("bd");
        topK.add("bf");
        topK.add("fe");
        System.out.println(topK.topk());
        topK.add("ae");
        System.out.println(topK.topk());
        topK.add("ae");
        System.out.println(topK.topk());
    }
}
