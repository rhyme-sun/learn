package learn.algorithm.basics.structure.tree.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题描述如下：
 * <p>
 * 这个公司现在要办 party，你可以决定哪些员工来，哪些员工不来，规则为：
 * <p>
 * - 如果某个员工来了，那么这个员工的所有直接下级都不能来；
 * - 派对的整体快乐值是所有到场员工快乐值的累加；
 * - 你的目标是让派对的整体快乐值尽量大；
 * - 给定一棵多叉树的头节点 boss，请返回派对的最大快乐值。
 */
public class Code08_PartyMaxHappy {

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

    static int maxHappy2(Employee boss) {
        if (boss == null) {
            return 0;
        }
        Info info = process(boss);
        return Math.max(info.yes, info.no);
    }

    private static Info process(Employee leader) {
        if (leader == null) {
            return new Info(0, 0);
        }
        int yes = leader.happy;
        int no = 0;
        for (Employee next : leader.nexts) {
            Info info = process(next);
            yes += info.no;
            no += Math.max(info.yes, info.no);
        }
        return new Info(yes, no);
    }

    private static class Info {
        // x 员工来了子树最大快乐值
        int yes;
        // x 员工没来子树最大快乐值
        int no;

        Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    private static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }
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
            int ans1 = maxHappy1(boss);
            int ans2 = maxHappy2(boss);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
