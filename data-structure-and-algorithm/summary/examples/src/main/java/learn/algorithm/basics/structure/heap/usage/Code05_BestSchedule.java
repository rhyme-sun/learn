package learn.algorithm.basics.structure.heap.usage;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 题目描述如下：
 * <p>
 * 给定一个数组 arr，arr[i] 代表第 i 号咖啡机泡一杯咖啡的时间；
 * 给定一个正数 N，表示 N 个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡；
 * <p>
 * 起始时刻为 0，返回所有人都获取到咖啡的最少时间。
 */
public class Code05_BestSchedule {

    /**
     * 求咖啡机的最优调度策略
     *
     * @param arr 咖啡机泡一杯咖啡需要的时间数组
     * @param n   员工总数
     * @return 所有人获取到咖啡的最少时间
     */
    static int bestSchedule(int[] arr, int n) {
        PriorityQueue<Machine> heap = new PriorityQueue<>(Comparator.comparing((Machine m) -> m.timePoint + m.workTime));
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int time = 0;
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            time = Math.max(time, cur.timePoint);
            heap.add(cur);
        }
        return time;
    }

    /**
     * 咖啡机
     */
    static class Machine {
        /**
         * 咖啡机可用时间点
         */
        public int timePoint;
        /**
         * 咖啡机泡一杯咖啡需要的时间
         */
        public int workTime;

        public Machine(int t, int w) {
            timePoint = t;
            workTime = w;
        }
    }

    public static void main(String[] args) {
        // 咖啡机
        int[] machines = {5, 3, 10};
        int n = 10;
        System.out.println(bestSchedule(machines, n));
        n = 11;
        System.out.println(bestSchedule(machines, n));
        n = 12;
        System.out.println(bestSchedule(machines, n));
    }
}
