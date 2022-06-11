package learn.algorithm.advance.structure.segmenttree;

/**
 * 线段树
 */
public class SegmentTree {

    /**
     * 线段树原始数据数组长度
     */
    int n;
    /**
     * 用来存放原始数组数据，下标从 1 开始
     */
    private int[] arr;
    /**
     * 表示线段树的每个节点，存放的节点对应数组区间的累加和
     */
    private int[] sum;
    /**
     * 懒数组，对应每个区间累加标志，初始都为 0
     * 比如，lazy[i]=m，表示 i 这个区间对应的元素需要全部增加 m
     */
    private int[] lazy;
    /**
     * 更新数组，记录对应区间的更新标志
     */
    private int[] change;
    /**
     * 记录对应区间是否更新
     */
    private boolean[] update;

    public SegmentTree(int[] origin) {
        // 线段树的下标从 1 开始，所以这里要将长度加 1
        n = origin.length + 1;
        arr = new int[n];
        for (int i = 1; i < n; i++) {
            arr[i] = origin[i - 1];
        }
        // 用来支持脑补概念中，某一个范围的累加和信息
        sum = new int[n << 2];
        // 用来支持脑补概念中，某一个范围沒有往下传递的累加任务
        lazy = new int[n << 2];
        // 用来支持脑补概念中，某一个范围有没有更新操作的任务
        change = new int[n << 2];
        // 用来支持脑补概念中，某一个范围更新任务，更新成了什么
        update = new boolean[n << 2];
    }

    public void build() {
        // 使用 arr 数组构建线段树，区间范围为 [1~N]，左闭右闭，N 表示 arr 数组中最后一个元素的下标
        // 从线段树的根节点从上往下构建（rt=1）
        build(1, n - 1, 1);
    }

    /**
     * @param l  线段节点代表区间左范围
     * @param r  线段节点代表区间右范围
     * @param rt 当前区间 [l,r] 对应线段树节点在 sum[] 数组中的位置（下标），
     *           和 l 和 r 合起来表示线段树上的一个节点信息
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
     * @param l 区间左边界
     * @param r 区间右边界
     * @param c 增加的值
     */
    public void add(int l, int r, int c) {
        // 从根节点开始考察，下发增加任务
        add(l, r, c, 1, n - 1, 1);
    }

    /**
     * 将原数组 L~R 范围内数增加 C。
     *
     * @param L  增加数组的左边界
     * @param R  增加数组的右边界
     * @param C  增加的值
     * @param l  线段区间左范围
     * @param r  线段区间右范围
     * @param rt 当前区间 [l,r] 对应线段树节点在 sum[] 数组中的位置（下标），
     *           和 l 和 r 合起来表示线段树上的一个节点信息
     */
    private void add(int L, int R, int C, int l, int r, int rt) {
        // L <= l~r <= R
        // 任务区间如果包含了当前的节点区间，在此范围收集信息，绝不会做任务之外的工作，下级节点暂不下发
        // 包住不下发，是线段树在范围新增能够达到 O(logN) 的本质
        if (L <= l && r <= R) {
            sum[rt] += C * (r - l + 1);
            lazy[rt] += C;
            return;
        }
        // 任务包不住节点区间，拆分区间，分配任务，以前被包住没有下发的旧任务要先下发一级
        int mid = (l + r) >> 1;
        // 下发旧任务信息
        pushDown(rt, mid - l + 1, r - mid);
        // 更新新任务信息
        if (L <= mid) {
            add(L, R, C, l, mid, rt << 1);
        }
        if (R > mid) {
            add(L, R, C, mid + 1, r, rt << 1 | 1);
        }
        // 将字节的增加状态同步奥父节点
        pushUp(rt);
    }

    /**
     * 区间更新
     *
     * @param l 区间左边界
     * @param r 区间右边界
     * @param c 修改的值
     */
    public void update(int l, int r, int c) {
        // 从根节点开始考察，下发更新任务
        update(l, r, c, 1, n - 1, 1);
    }

    /**
     * 在 L~R 范围内将所有元素的值变为 C
     *
     * @param L  增加数组的左边界
     * @param R  增加数组的右边界
     * @param C  增加的值
     * @param l  线段区间左范围
     * @param r  线段区间右范围
     * @param rt 区间范围在数组中的下标
     */
    private void update(int L, int R, int C, int l, int r, int rt) {
        // 全包了，更新范围边界
        if (L <= l && r <= R) {
            update[rt] = true;
            change[rt] = C;
            sum[rt] = C * (r - l + 1);
            lazy[rt] = 0;
            return;
        }
        // 包不住，先下发
        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);
        if (L <= mid) {
            update(L, R, C, l, mid, rt << 1);
        }
        if (R > mid) {
            update(L, R, C, mid + 1, r, rt << 1 | 1);
        }
        // 将子节点更新状态同步到父节点
        pushUp(rt);
    }

    /**
     * 将 index 位置的更新为 c
     *
     * @param index 更新位置
     * @param c     增加的值
     */
    public void change(int index, int c) {
        update(index, index, c);
    }

    /**
     * 区间求和
     *
     * @param l 区间左边界
     * @param r 区间右边界
     * @return [l,r] 区间累加和
     */
    public int query(int l, int r) {
        return query(l, r, 1, n-1, 1);
    }

    /**
     * 查询 L~R 范围累加和
     *
     * @param L  增加数组的左边界
     * @param R  增加数组的右边界
     * @param l  线段区间左范围
     * @param r  线段区间右范围
     * @param rt 区间范围在数组中的下标
     */
    private int query(int L, int R, int l, int r, int rt) {
        if (L <= l && r <= R) {
            return sum[rt];
        }
        int mid = (l + r) >> 1;
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

    /**
     * 用子节点对应的值构建父节点
     *
     * @param rt 父节点在 sum[] 数组中的位置
     */
    private void pushUp(int rt) {
        sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
    }

    /**
     * 之前的，所有懒增加，和懒更新，从父范围，下发给左右两个子范围
     *
     * @param rt 父级范围对应下标
     * @param ln 左子树元素结点个数
     * @param rn 右子树元素结点个数
     */
    private void pushDown(int rt, int ln, int rn) {
        if (update[rt]) {
            update[rt << 1] = true;
            update[rt << 1 | 1] = true;
            change[rt << 1] = change[rt];
            change[rt << 1 | 1] = change[rt];
            lazy[rt << 1] = 0;
            lazy[rt << 1 | 1] = 0;
            sum[rt << 1] = change[rt] * ln;
            sum[rt << 1 | 1] = change[rt] * rn;
            update[rt] = false;
        }
        // 懒信息下发一层
        if (lazy[rt] != 0) {
            lazy[rt << 1] += lazy[rt];
            lazy[rt << 1 | 1] += lazy[rt];
            sum[rt << 1] += lazy[rt] * ln;
            sum[rt << 1 | 1] += lazy[rt] * rn;
            lazy[rt] = 0;
        }
    }

    // for test
    static class Right {

        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

        public void change(int index, int C) {
            update(index, index, C);
        }
    }

    public static void main(String[] args) {
        int[] origin = {2, 1, 1, 2, 3, 4, 5};
        SegmentTree seg = new SegmentTree(origin);
        int l = 2;
        int r = 5;
        int c = 4;
        seg.build();
        seg.add(l, r, c);
        seg.update(l, r, c);

        int sum = seg.query(l, r);
        System.out.println(sum);

        test();
    }

    private static void test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = generateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int N = origin.length;
            seg.build();
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int l = Math.min(num1, num2);
                int r = Math.max(num1, num2);
                int c = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.4) {
                    seg.add(l, r, c);
                    rig.add(l, r, c);
                } else if (Math.random() < 0.7) {
                    seg.update(l, r, c);
                    rig.update(l, r, c);
                } else {
                    seg.change(l, c);
                    rig.change(l, c);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int l = Math.min(num1, num2);
                int r = Math.max(num1, num2);
                long ans1 = seg.query(l, r);
                long ans2 = rig.query(l, r);
                if (ans1 != ans2) {
                    System.out.println("Oops!");
                    System.out.println(ans1);
                    System.out.println(ans2);
                    break;
                }
            }
        }
        System.out.println("Finish!");
    }

    private static int[] generateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }
}
