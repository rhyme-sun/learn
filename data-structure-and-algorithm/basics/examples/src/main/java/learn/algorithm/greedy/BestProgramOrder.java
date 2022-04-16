package learn.algorithm.greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 题目描述如下：
 * 对于正数数组 costs、正数数组 profits、正数 K、正数 M。
 * 其中 costs[i] 表示 i 号项目的花费；
 * profits[i] 表示 i 号项目在扣除花费之后挣到的前（利润）；
 * K 表示你只能串行的最多做 K 个项目；
 * M 表示你初始的资金。
 * 每做完一个项目，马上获得的收益，可以支持你去做下一个项目，不能并行的做项目，求你最后获得的最大钱数
 */
public class BestProgramOrder {

    static class Program {
        /**
         * 项目净利润
         */
        public int p;
        /**
         * 项目花费
         */
        public int c;

        public Program(int p, int c) {
            this.p = p;
            this.c = c;
        }
    }

    /**
     * 求做完项目后的最大资金
     *
     * @param k       项目上限
     * @param w       本金
     * @param profits 项目净利润
     * @param costs   项目花费
     * @return 最大资金
     */
    static int findMaximizedCapital(int k, int w, int[] profits, int[] costs) {
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < profits.length; i++) {
            minCostQ.add(new Program(profits[i], costs[i]));
        }
        for (int i = 0; i < k; i++) {
            while (!minCostQ.isEmpty() && minCostQ.peek().c <= w) {
                maxProfitQ.add(minCostQ.poll());
            }
            if (maxProfitQ.isEmpty()) {
                return w;
            }
            w += maxProfitQ.poll().p;
        }
        return w;
    }

    static class MinCostComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.c - o2.c;
        }
    }

    static class MaxProfitComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o2.p - o1.p;
        }
    }
}
