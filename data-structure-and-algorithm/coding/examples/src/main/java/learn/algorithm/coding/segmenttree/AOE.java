package learn.algorithm.coding.segmenttree;

import java.util.Arrays;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定两个非负数组 x 和 hp，长度都是 N，再给定一个正数 range。
 * x 有序，x[i] 表示 i 号怪兽在 x 轴上的位置；hp[i] 表示 i 号怪兽的血量；range 表示法师释放技能的范围长度。
 * 每只怪兽每次被击中损失 1 点血量，返回要把所有怪兽血量清空，至少需要释放多少次 AOE 技能？
 */
public class AOE {

    static int minTimes(int[] x, int[] hp, int range) {
        if (x == null || x.length == 0 || hp == null || hp.length < x.length || range < 1) {
            return 0;
        }
        int n = x.length;
        // cover[i] 表示窗口左侧在 i 位置时，r 能够达到的最大位置的下一个
        int[] cover = new int[n];
        int r = 0;
        for (int i = 0; i < n; i++) {
            while (r < n && x[r] - x[i] <= range) {
                r++;
            }
            cover[i] = r;
        }
        int res = 0;
        for (int l = 0; l < n; l++) {
            if (hp[l] > 0) {
                int minus = hp[l];
                for (int i = l; i < cover[l]; i++) {
                    hp[i] -= minus;
                }
                res += minus;
            }
        }
        return res;
    }

    /**
     * 使用线段树优化血量区间更新
     */
    static int minTimes2(int[] x, int[] hp, int range) {
        if (x == null || x.length == 0 || hp == null || hp.length < x.length || range < 1) {
            return 0;
        }
        int n = x.length;
        // cover[i] 表示窗口左侧在 i 位置时，r 能够达到的最大位置的下一个
        int[] cover = new int[n];
        int r = 0;
        for (int i = 0; i < n; i++) {
            while (r < n && x[r] - x[i] <= range) {
                r++;
            }
            cover[i] = r;
        }

        SegmentTree seg = new SegmentTree(hp);
        seg.build();
        int res = 0;
        for (int l = 0; l < n; l++) {
            int leftHp = seg.query(l + 1, l + 1);
            if (leftHp > 0) {
                res += leftHp;
                // l~cover[l]
                seg.add(l + 1, cover[l], -leftHp);
            }
        }
        return res;
    }

    static class SegmentTree {

        private int n;
        private int[] arr;
        private int[] sum;
        /**
         * 标记区间增加值
         */
        private int[] lazy;

        public SegmentTree(int[] origin) {
            n = origin.length + 1;
            arr = new int[n];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }

            sum = new int[n << 2];
            lazy = new int[n << 2];
        }

        public void build() {
            // 从线段树根节点开始构建，rt=1
            build(1, n - 1, 1);
        }

        /**
         * 当前在 [l,r] 范围，考虑线段树的构建
         *
         * @param l  区间左边界
         * @param r  区间右边界
         * @param rt 区间对应节点在 sum[] 中的位置，和 l、r 一起构成线段树的节点信息
         */
        private void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        /**
         * 区间增加
         *
         * @param l 区间右边界
         * @param r 区间左边界
         * @param c 增加值
         */
        public void add(int l, int r, int c) {
            add(l, r, c, 1, n - 1, 1);
        }

        /**
         * 区间增加递归函数，当前来到 rt 节点，区间范围为 [l,r]
         *
         * @param L  任务区间左边界
         * @param R  任务区间右边界
         * @param C  增加值
         * @param l  节点区间左边界
         * @param r  节点区间右边界
         * @param rt 区间对应节点在 sum[] 中的位置，和 l、r 一起构成线段树的节点信息
         */
        private void add(int L, int R, int C, int l, int r, int rt) {
            // 任务区间包住了节点区间
            if (L <= l && R >= r) {
                sum[rt] += (r - l + 1) * C;
                lazy[rt] += C;
                return;
            }
            // 任务下发
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);

            // 子节点继续执行增加任务
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        /**
         * 区间求和
         *
         * @param l 区间左边界
         * @param r 区间右边界
         * @return [l, r] 区间累加和
         */
        public int query(int l, int r) {
            return query(l, r, 1, n - 1, 1);
        }

        private int query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return sum[rt];
            }
            int mid = (r + l) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            int ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

        private void pushUp(int rt) {
            // sum[rt] = sum[rt * 2] + sum[rt * 2 + 1]
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        /**
         * 任务下发
         *
         * @param rt 线段树父节点位置
         * @param ln 线段树左侧孩子节点对应区间长度
         * @param rn 线段树右侧孩子节点对应区间长度
         */
        private void pushDown(int rt, int ln, int rn) {
            if (lazy[rt] != 0) {
                // 左侧
                lazy[rt << 1] += lazy[rt];
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1] += ln * lazy[rt];
                sum[rt << 1 | 1] += rn * lazy[rt];
                lazy[rt] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int N = 50;
        int X = 500;
        int H = 60;
        int R = 10;
        int testTime = 50000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[] x2 = ArrayComparator.generatePositiveRandomArray(len, X);
            Arrays.sort(x2);
            int[] hp2 = ArrayComparator.generatePositiveRandomArray(len, H);
            int[] x3 = ArrayComparator.copyArray(x2);
            int[] hp3 = ArrayComparator.copyArray(hp2);
            int range = (int) (Math.random() * R) + 1;
            int ans1 = minTimes(x2, hp2, range);
            int ans2 = minTimes2(x3, hp3, range);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("Finish!");
    }
}
