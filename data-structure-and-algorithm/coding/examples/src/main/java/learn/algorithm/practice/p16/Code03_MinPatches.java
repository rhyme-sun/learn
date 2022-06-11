package learn.algorithm.practice.p16;

public class Code03_MinPatches {

    static int minPatches(int[] nums, int n) {
        int patches = 0;
        long range = 0;
        // 使用数组提供的值扩充 range
        for (int i = 0; i < nums.length; i++) {
            while (range + 1 < nums[i]) {
                range += range + 1;
                patches++;
                if (range >= n) {
                    return patches;
                }
            }
            range += nums[i];
            if (range >= n) {
                return patches;
            }
        }
        // 覆盖 n 还至少需要添加几个数
        while (range < n) {
            range += range + 1;
            patches++;
        }
        return patches;
    }

    public static void main(String[] args) {
        int[] test = {1, 2, 31, 33};
        int n = 2147483647;
        System.out.println(minPatches(test, n));
    }
}
