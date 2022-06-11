package learn.algorithm.practice.p07;

/**
 * 题目描述如下：
 * 给定一个二叉树，我们在树的节点上安装摄像头。节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。计算监控树的所有节点所需的最小摄像头数量。
 * https://leetcode.cn/problems/binary-tree-cameras/
 */
public class Code02_MinCameraCover {

    static int minCameraCover(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Info process = process(root);
        return (int) Math.min(process.uncovered + 1, Math.min(process.coveredHasCamera, process.coveredNoCamera));
    }

    private static Info process(TreeNode x) {
        if (x == null) {
            // 空树认为是被覆盖且不用放相机的，所以：
            // 没有覆盖设置为系统最大值（可以理解为无效值，因为回溯时比较最小值一定会被淘汰掉）；
            // 覆盖且不用放相机设置为 0；
            // 覆盖且放了相机设置为系统最大值（可以理解为无效值）
            return new Info(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        }
        Info left = process(x.left);
        Info right = process(x.right);

        // 6 种最小值情况两两组合讨论得最小值
        // x 节点不被覆盖，但左右子树被覆盖
        long uncovered = left.coveredNoCamera + right.coveredNoCamera;
        long coveredNoCamera = Math.min(left.coveredHasCamera + right.coveredHasCamera,
                Math.min(left.coveredHasCamera + right.coveredNoCamera, left.coveredNoCamera + right.coveredHasCamera));
        long coveredHasCamera = Math.min(left.uncovered, Math.min(left.coveredNoCamera, left.coveredHasCamera))
                + Math.min(right.uncovered, Math.min(right.coveredNoCamera, right.coveredHasCamera)) + 1;
        return new Info(uncovered, coveredNoCamera, coveredHasCamera);
    }

    /**
     * 以 x 为头节点的子树，提供的信息（x 的子节点全部被覆盖）
     */
    private static class Info {
        /**
         * x 没有被覆盖且没有相机，最少相机数量
         */
        private long uncovered;
        /**
         * x 被覆盖且 x 没有放置相机，最少相机数量
         */
        private long coveredNoCamera;
        /**
         * x 被覆盖且 x 放置了相机，最少相机数量
         */
        private long coveredHasCamera;

        Info(long uncovered, long no, long has) {
            this.uncovered = uncovered;
            this.coveredNoCamera = no;
            this.coveredHasCamera = has;
        }
    }

    private static class TreeNode {
        public int value;
        public TreeNode left;
        public TreeNode right;
    }

    static int minCameraCover2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Info2 info2 = process2(root);
        return info2.cameras + (info2.status == Status.UNCOVERED ? 1 : 0);
    }

    private static Info2 process2(TreeNode x) {
        // 空树被认为是被覆盖且无相机
        if (x == null) {
            return new Info2(Status.COVERED_N0_CAMERA, 0);
        }
        Info2 left = process2(x.left);
        Info2 right = process2(x.right);
        // 注意，下面的贪心策略不能调换顺序，即如果 x 一个子树为 UNCOVERED，另一个子树为 COVERED_HAS_CAMERA，要优先执行 UNCOVERED
        // 贪心策略一：左右子树有任意一个子树没覆盖，那么 x 节点一定要放个相机
        if (left.status == Status.UNCOVERED || right.status == Status.UNCOVERED) {
            return new Info2(Status.COVERED_HAS_CAMERA, left.cameras + right.cameras + 1);
        }
        // 贪心策略二：左右子树有任意一个存在相机，那么 x 节点不用放相机且已经被覆盖了
        if (left.status == Status.COVERED_HAS_CAMERA || right.status == Status.COVERED_HAS_CAMERA) {
            return new Info2(Status.COVERED_N0_CAMERA, left.cameras + right.cameras);
        }
        // 贪心策略三：左右子树都没放相机，但都被覆盖了，这时候将决策交给 x 的父节点，x 选择不放相机
        return new Info2(Status.UNCOVERED, left.cameras + right.cameras);
    }

    private static class Info2 {
        private Status status;
        private int cameras;

        Info2(Status status, int cameras) {
            this.status = status;
            this.cameras = cameras;
        }
    }

    private enum Status {
        UNCOVERED, COVERED_N0_CAMERA, COVERED_HAS_CAMERA
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        root.left = new TreeNode();
        root.right = new TreeNode();
        root.right.right = new TreeNode();
        int ans1 = minCameraCover(root);
        int ans2 = minCameraCover2(root);
        System.out.println(ans1);
        System.out.println(ans2);
    }
}
