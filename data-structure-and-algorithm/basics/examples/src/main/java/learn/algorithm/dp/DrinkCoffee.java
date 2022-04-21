package learn.algorithm.dp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import learn.algorithm.comparator.ArrayComparator;

// 题目
// 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
// 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
// 认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
// 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
// 洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
// 四个参数：arr, n, a, b
// 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。

/**
 * 喝咖啡问题，问题描述如下：
 * 给定一个数组 arr，arr[i] 代表第 i 号咖啡机泡一杯咖啡的时间；
 * 给定一个正数 N，表示 N 个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡；
 * 只有一台洗杯子机，一次只能洗一个杯子，时间耗费 a，洗完才能洗下一杯；
 * 每个咖啡杯也可以自己挥发干净，时间耗费 b，咖啡杯可以并行挥发；
 * 假设所有人拿到咖啡之后立刻喝干净，返回所有人去获取咖啡再到全部杯子都变干净的时间。
 */
public class DrinkCoffee {

    /**
     * 验证的方法，暴力实现
     */
    static int drinkCoffee1(int[] arr, int n, int wash, int air) {
        if (arr == null || arr.length == 0 || n <= 0) {
            return 0;
        }
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, wash, air);
    }

    /**
     * 每个人暴力尝试用每一个咖啡机给自己做咖啡
     */
    private static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int wash, int air) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, wash, air, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, wash, air));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

    private static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }

    /**
     * 求咖啡机的最优调度策略
     *
     * @param arr 咖啡机泡一杯咖啡需要的时间数组
     * @param n   员工总数
     * @return 在最优调度策略下，员工获取到咖啡时间点数组（递增）
     */
    static int[] bestSchedule(int[] arr, int n) {
        PriorityQueue<Machine> heap = new PriorityQueue<>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return drinks;
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

    static class MachineComparator implements Comparator<Machine> {

        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }
    }

    /**
     * 喝咖啡，递归尝试
     *
     * @param arr  咖啡机泡一杯咖啡需要的时间数组
     * @param n    员工总数
     * @param wash 洗杯机洗一杯咖啡需要的时间（串行）
     * @param air  挥发干净时间（并行）
     * @return 全体员工完成一次和咖啡活动需要的最短时间
     */
    static int drinkCoffee2(int[] arr, int n, int wash, int air) {
        if (arr == null || arr.length == 0 || n <= 0) {
            return 0;
        }
        // 获取所有员工从咖啡机获取到咖啡的最优调度策略
        int[] drinks = bestSchedule(arr, n);
        return bestTime(drinks, wash, air, 0, 0);
    }

    /**
     * 当前来到 i 号员工，考虑其怎么选择才能让全体杯子变干净的最优时间
     *
     * @param drinks 员工获取到咖啡时间数组，题目要求 0 秒喝完咖啡，那么该数组也表示员工开始准备洗杯子的时间数组
     * @param wash   洗杯机洗一杯咖啡需要的时间（串行）
     * @param air    挥发干净时间（并行）
     * @param index  员工序号
     * @param free   洗杯机可用时间点
     * @return 全体杯子变干净的最优时间
     */
    private static int bestTime(int[] drinks, int wash, int air, int index, int free) {
        if (index == drinks.length) {
            return 0;
        }
        // 第一种可能性：index 号杯子决定洗
        // 自己杯子变干净的时间
        int selfClean1 = Math.max(drinks[index], free) + wash;
        // 其他杯子变干净的最短时间
        int restClean1 = bestTime(drinks, wash, air, index + 1, selfClean1);
        // 那么全体杯子变干净的时间就是上述的最大值（木桶效应）
        int p1 = Math.max(selfClean1, restClean1);

        // 第二种可能性：index 号杯子决定挥发
        // 自己杯子变干净的时间
        int selfClean2 = drinks[index] + air;
        // 其他杯子变干净的最短时间
        int restClean2 = bestTime(drinks, wash, air, index + 1, free);
        // 那么全体杯子变干净的时间就是上述的最大值（木桶效应）
        int p2 = Math.max(selfClean2, restClean2);

        // 两种可能性的最小值就是要求的杯子变干净的最短时间
        return Math.min(p1, p2);
    }

    /**
     * 动态规划优化
     */
    static int drinkCoffee3(int[] arr, int n, int wash, int air) {
        if (arr == null || arr.length == 0 || n <= 0) {
            return 0;
        }
        // 获取所有员工从咖啡机获取到咖啡的最优调度策略
        int[] drinks = bestSchedule(arr, n);
        return bestTimeDp(drinks, wash, air);
    }

    private static int bestTimeDp(int[] drinks, int wash, int air) {
        int N = drinks.length;
        int maxFree = 0;
        for (int i = 0; i < drinks.length; i++) {
            maxFree = Math.max(maxFree, drinks[i]) + wash;
        }
        int[][] dp = new int[N + 1][maxFree + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int free = 0; free <= maxFree; free++) {
                int selfClean1 = Math.max(drinks[index], free) + wash;
                if (selfClean1 > maxFree) {
                    // 因为后面的也都不用填了
                    break;
                }
                // index号杯子 决定洗
                int restClean1 = dp[index + 1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);
                // index号杯子 决定挥发
                int selfClean2 = drinks[index] + air;
                int restClean2 = dp[index + 1][free];
                int p2 = Math.max(selfClean2, restClean2);
                dp[index][free] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayComparator.generatePositiveRandomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int wash = (int) (Math.random() * 7) + 1;
            int air = (int) (Math.random() * 10) + 1;
            int ans1 = drinkCoffee1(arr, n, wash, air);
            int ans2 = drinkCoffee2(arr, n, wash, air);
            int ans3 = drinkCoffee3(arr, n, wash, air);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println("n : " + n);
                System.out.println("wash : " + wash);
                System.out.println("air : " + air);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
