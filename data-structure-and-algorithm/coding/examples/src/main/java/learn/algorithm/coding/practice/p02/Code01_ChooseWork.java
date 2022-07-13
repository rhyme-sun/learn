package learn.algorithm.coding.practice.p02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * 题目描述如下：
 * 给定数组 hard 和 money，长度都为 N，`hard[i]` 表示 i 号工作的难度， `money[i]` 表示 i 号工作的收入。
 * <p>
 * 现有一个长度为 M 的数组 ability，`ability[j]` 表示 j 号人的能力。要求每个人选择一份工作（不同的人可以选择同一份工作），
 * 人的能力必须大于等于这份工作的难度才能够胜任这份工作，求每个人可以获得的最大收入。
 */
public class Code01_ChooseWork {

    static int[] bestTask(int[] hard, int[] money, int[] ability) {
        if (hard == null || hard.length == 0 || money == null || money.length == 0 ||
                ability == null || ability.length == 0) {
            return null;
        }
        int n = hard.length;
        Task[] tasks = new Task[n];
        for (int i = 0; i < n; i++) {
            tasks[i] = new Task(hard[i], money[i]);
        }
        return bestTask(tasks, ability);
    }

    private static int[] bestTask(Task[] tasks, int[] ability) {
        Arrays.sort(tasks, new TaskComp());
        // key 为工作难度， value 为 money
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(tasks[0].hard, tasks[0].money);

        // 记录上一个工作
        Task pre = tasks[0];
        for (int i = 0; i < tasks.length; i++) {
            Task cur = tasks[i];
            // 相同难度选择第一个，难度大且钱少的工作不要
            if (cur.hard != pre.hard && cur.money > pre.money) {
                map.put(cur.hard, cur.money);
                pre = cur;
            }
        }
        int[] res = new int[ability.length];
        for (int i = 0; i < ability.length; i++) {
            Integer key = map.floorKey(ability[i]);
            if (key != null) {
                res[i] = map.get(key);
            }
        }
        return res;
    }

    static class Task {
        private int hard;
        private int money;

        public Task(int hard, int money) {
            this.hard = hard;
            this.money = money;
        }
    }

    static class TaskComp implements Comparator<Task> {

        @Override
        public int compare(Task o1, Task o2) {
            // 按照工作难度递增排序，难度相同，钱多的放前面
            return o1.hard != o2.hard ? o1.hard - o2.hard : -(o1.money - o2.money);
        }
    }

    public static void main(String[] args) {
        int[] hard = new int[]{3, 3, 5, 2, 1, 8};
        int[] money = new int[]{6, 7, 2, 8, 3, 18};
        int[] ability = new int[]{4, 9, 1};
        int[] ans = bestTask(hard, money, ability);
        System.out.println(Arrays.toString(ans));
    }
}
