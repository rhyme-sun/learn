package learn.algorithm.practice.p06;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 数组中所有数都异或起来的结果，叫做异或和，给定一个数组 arr，返回 arr 的最大子数组异或和。
 */
public class Code01_MaxEOR {

    /**
     * O(N^2) 的解
     */
    static int maxXorSubarray1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        // eorArr[i] 表示 arr[1..i] 的异或和
        int[] xorArr = new int[n];
        int xor = 0;
        for (int i = 0; i < n; i++) {
            xorArr[i] = xor ^ arr[i];
        }

        int maxXor = xorArr[0];
        // 依次讨论以 i 结尾子数组的最大异或和
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= i; j++) {
                maxXor = Math.max(maxXor, j == 0 ? xorArr[i] : xorArr[i] ^ xorArr[j - 1]);
            }
        }
        return maxXor;
    }

    // O(N)
    static int maxXorSubarray2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        NumTrie numTrie = new NumTrie();
        numTrie.add(0);
        // 0~i整体异或和
        int xor = 0;
        for (int i = 0; i < arr.length; i++) {
            xor ^= arr[i]; // 0 ~ i
            max = Math.max(max, numTrie.maxEor(xor));
            numTrie.add(xor);
        }
        return max;
    }

    private static class Node {

        private Node[] nexts = new Node[2];
    }

    private static class NumTrie {
        // 头节点
        private Node head = new Node();

        public void add(int newNum) {
            Node cur = head;
            for (int move = 31; move >= 0; move--) {
                // 从高位到地位取得每个位置上的值
                int path = ((newNum >> move) & 1);
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                cur = cur.nexts[path];
            }
        }

        // 该结构之前收集了一票数字，并且建好了前缀树
        // num 和谁 ^ 最大的结果（把结果返回）
        public int maxEor(int num) {
            Node cur = head;
            int ans = 0;
            for (int move = 31; move >= 0; move--) {
                // 取出 num 中第 move位的状态，path 只有两种值 0 和 1
                int path = (num >> move) & 1;
                // 期待遇到的值，符号位的贪心策略和其他位置不同，符号位为 1，那么希望遇到 1，这样异或完就是非负数，值最大
                // 其他位置希望遇到和自己相反的值，这样异或后的值才最大
                int best = move == 31 ? path : (path ^ 1);
                // 实际能遇到的值
                best = cur.nexts[best] != null ? best : (best ^ 1);
                // (path ^ best) 将当前位值得到最优结果设置到 ans 中去
                ans |= (path ^ best) << move;
                cur = cur.nexts[best];
            }
            return ans;
        }
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 50;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxSize, maxValue);
            int comp = maxXorSubarray1(arr);
            int res = maxXorSubarray2(arr);
            if (res != comp) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
