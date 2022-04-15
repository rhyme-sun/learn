package learn.algorithm.structure.tree.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * 求派对最大快乐值，
 */
public class PartyMaxHappy {

    static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }
    }

    /**
     * 方法 1
     */
    static int maxHappy1(Employee boss) {
        if (boss == null) {
            return 0;
        }
        return process(boss, false);
    }

    /**
     * 当前来到的节点叫 cur 时，求最大快乐值
     * 如果 up 为 true，返回在 cur 上级已经确定来的情况下，cur 整棵树能够提供最大的快乐值；
     * 如果 up 为 false，返回在 cur 上级已经确定不来的情况下，cur 整棵树能够提供最大的快乐值
     *
     * @param cur 当前节点
     * @param up  当前节点上级节点是否来
     * @return 最大快乐值
     */
    private static int process(Employee cur, boolean up) {
        if (up) { // 如果 cur 的上级来的话，cur 没得选，只能不来
            int ans = 0;
            for (Employee next : cur.nexts) {
                ans += process(next, false);
            }
            return ans;
        } else { // 如果 cur 的上级不来的话，cur 可以来也可以不来
            // p1 表示 cur 上级来的最大快乐值
            int p1 = cur.happy;
            // p2 表示 cur 上级不来的最大快乐值
            int p2 = 0;
            for (Employee next : cur.nexts) {
                p1 += process(next, true);
                p2 += process(next, false);
            }
            return Math.max(p1, p2);
        }
    }

    /**
     * 方法 2
     */
    static int maxHappy2(Employee boss) {
        Info allInfo = process(boss);
        return Math.max(allInfo.no, allInfo.yes);
    }

    /**
     * 某个节点 x 的子树信息
     */
    static class Info {
        /**
         * 子树头节点不来的最大快乐值
         */
        int no;
        /**
         * 子树头节点来的最大快乐值
         */
        int yes;

        public Info(int n, int y) {
            no = n;
            yes = y;
        }
    }

    static Info process(Employee x) {
        if (x == null) {
            return new Info(0, 0);
        }
        int no = 0;
        int yes = x.happy;
        for (Employee next : x.nexts) {
            Info nextInfo = process(next);
            no += Math.max(nextInfo.no, nextInfo.yes);
            yes += nextInfo.no;
        }
        return new Info(no, yes);
    }

    // for test
    private static Employee generateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        generateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    // for test
    private static void generateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextsSize; i++) {
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            e.nexts.add(next);
            generateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNexts = 7;
        int maxHappy = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = generateBoss(maxLevel, maxNexts, maxHappy);
            if (maxHappy1(boss) != maxHappy2(boss)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
