package learn.algorithm.structure.segmenttree;

/**
 *
 */
public class SegmentTree {


    private int MAXN;
    /**
     * 存放原始数组元素，下标从 1 开始
     */
    private int[] arr;
    /**
     * 区间累加和数组，模拟线段树，维护累加和
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
        MAXN = origin.length + 1;
        // 从 1 开始使用
        arr = new int[MAXN];
        for (int i = 1; i < MAXN; i++) {
            arr[i] = origin[i - 1];
        }
        sum = new int[MAXN << 2]; // 用来支持脑补概念中，某一个范围的累加和信息
        lazy = new int[MAXN << 2]; // 用来支持脑补概念中，某一个范围沒有往下傳遞的纍加任務
        change = new int[MAXN << 2]; // 用来支持脑补概念中，某一个范围有没有更新操作的任务
        update = new boolean[MAXN << 2]; // 用来支持脑补概念中，某一个范围更新任务，更新成了什么
    }

    /**
     * 构建线段树
     *
     * @param l  线段区间左范围
     * @param r  线段区间右范围
     * @param rt 区间范围在 sum[] 中的下标
     */
    public void build(int l, int r, int rt) {
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
     * 将原数组 L~R 范围内数增加 C。
     *
     * @param L  增加数组的左边界
     * @param R  增加数组的右边界
     * @param C  增加的值
     * @param l  线段区间左范围
     * @param r  线段区间右范围
     * @param rt 区间范围在数组中的下标
     */
    public void add(int L, int R, int C, int l, int r, int rt) {
        // L <= l~r <= R
        // 任务如果把此时的范围全包了，在此范围收集信息，下级节点暂不下发
        // 包住不下发，是线段树在范围新增能够达到 O(logN) 的本质
        if (L <= l && r <= R) {
            sum[rt] += C * (r - l + 1);
            lazy[rt] += C;
            return;
        }
        // 任务没有把你全包，拆分区间，分配任务，以前被包住没有下发的旧任务要先下发一级
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
        pushUp(rt);
    }

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
            sum[rt << 1] += lazy[rt] * ln;
            lazy[rt << 1 | 1] += lazy[rt];
            sum[rt << 1 | 1] += lazy[rt] * rn;
            lazy[rt] = 0;
        }
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
    public void update(int L, int R, int C, int l, int r, int rt) {
        // 全包了，更新范围边界
        if (L <= l && r <= R) {
            update[rt] = true;
            change[rt] = C;
            sum[rt] = C * (r - l + 1);
            lazy[rt] = 0;
            return;
        }
        // 当前任务躲不掉，无法懒更新，要往下发
        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);
        if (L <= mid) {
            update(L, R, C, l, mid, rt << 1);
        }
        if (R > mid) {
            update(L, R, C, mid + 1, r, rt << 1 | 1);
        }
        pushUp(rt);
    }

    /**
     * 将 index 位置的更新为 C
     *
     * @param index 更新位置
     * @param C     增加的值
     * @param l     线段区间左范围
     * @param r     线段区间右范围
     * @param rt    区间范围在数组中的下标
     */
    public void change(int index, int C, int l, int r, int rt) {
        update(index, index, C, l, r, rt);
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
    public long query(int L, int R, int l, int r, int rt) {
        if (L <= l && r <= R) {
            return sum[rt];
        }
        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);
        long ans = 0;
        if (L <= mid) {
            ans += query(L, R, l, mid, rt << 1);
        }
        if (R > mid) {
            ans += query(L, R, mid + 1, r, rt << 1 | 1);
        }
        return ans;
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
        int S = 1; // 整个区间的开始位置，规定从1开始，不从 0 开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是 N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是 1，不是 0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在 [S,N] 整个范围上 build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);

        System.out.println(sum);
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));
    }

    private static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = generateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.4) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else if (Math.random() < 0.7) {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                } else {
                    seg.change(L, C, S, N, root);
                    rig.change(L, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
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
