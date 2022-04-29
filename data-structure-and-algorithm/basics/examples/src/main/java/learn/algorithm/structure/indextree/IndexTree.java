package learn.algorithm.structure.indextree;

/**
 * 快速求得数组某个范围内的累加和，并且当原数组某个值变化时更新代价低。
 * 前缀和数组也可以用来解决范围累加和问题，但数组元素变化时更新代价价较高。
 */
public class IndexTree {

    // 下标从 1 开始
    private int[] tree;
    private int N;

    public IndexTree(int size) {
        N = size;
        tree = new int[N + 1];
    }

    // 1~index 累加和是多少？
    public int sum(int index) {
        int ret = 0;
        while (index > 0) {
            ret += tree[index];
            index -= index & -index;
        }
        return ret;
    }

    // index & -index :  提取出 index 最右侧的 1 出来
    // index :           0011001000
    // index & -index :  0000001000
    public void add(int index, int d) {
        while (index <= N) {
            tree[index] += d;
            index += index & -index;
        }
    }

    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }

    }

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 2000000;
        IndexTree tree = new IndexTree(N);
        Right test = new Right(N);
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * V);
                tree.add(index, add);
                test.add(index, add);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("Finish!");
    }
}
